package net.lab1024.sa.base.handler;

import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.Condition;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.*;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ConditionInterceptor implements InnerInterceptor {

    // 顶层注入逻辑已改为括号深度扫描，移除原基于正则的子句匹配常量

    // 用于在 SQL 字符串中查找潜在的别名及其后的点号和列名的正则表达式模板。
    // 捕获组 1 捕获别名。查找点号前的别名，并在其周围及列名后有单词边界或字符串开头/结尾。
    // 这是一个尽力而为的正则表达式，可能无法处理所有复杂的 SQL 边缘情况
    // （例如注释中的别名、字符串常量中的别名、复杂表达式）。
    private static final String ALIAS_COLUMN_REGEX_TEMPLATE = "(?:^|\\W)([a-zA-Z0-9_]+)\\.%s(?:\\W|$)";
    // 用于缓存针对每个列名编译好的正则表达式 Pattern
    private final Map<String, Pattern> aliasColumnPatternCache = new java.util.concurrent.ConcurrentHashMap<>();

    // 用于缓存从 ResultMap 中提取的属性 (property) -> 别名 (alias) 映射。
    // 对于 column 中包含别名的显式 ResultMap，这是可靠的。
    // 对于 resultType 自动生成的 ResultMap，如果 alias 未保留在 column 名中，则不可靠。
    private final Map<String, Map<String, String>> msFieldAliasCache = new java.util.concurrent.ConcurrentHashMap<>();

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter,
            RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {

        // 仅处理 SELECT 操作
        if (!ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
            return;
        }

        List<Condition.Criterion> criteriaList = extractConditions(parameter);
        if (criteriaList.isEmpty()) {
            return;
        }

        // 1. 尝试从 ResultMap 构建别名映射（对显式 ResultMap 可靠，对 resultType 可靠性较低）
        Map<String, String> resultMapAliases = buildFieldToAliasMap(ms);

        List<String> whereClauses = new ArrayList<>();
        List<Object> extraParams = new ArrayList<>();
        List<String> orderByClauses = new ArrayList<>();

        for (Condition.Criterion criterion : criteriaList) {
            // 处理单个条件，尝试使用 ResultMap 别名和 SQL 字符串搜索来查找别名。
            // 将 ResultMap 别名映射和 BoundSql 传递给 processCriterion。
            processCriterion(criterion, whereClauses, extraParams, orderByClauses, resultMapAliases, boundSql);
        }

        // 如果有 WHERE 条件或 ORDER BY 排序，则修改 SQL
        if (!whereClauses.isEmpty() || !orderByClauses.isEmpty()) {
            String newSql = buildNewSql(boundSql.getSql(), whereClauses, orderByClauses);
            // 调试输出：打印原始 SQL 和新生成的 SQL
            resetBoundSql(boundSql, newSql, parameter, extraParams, ms.getConfiguration());
        }
    }

    /**
     * 从参数对象中提取 Condition 对象列表。
     * 支持提取作为根参数或 Map 参数值中的 Condition 对象。
     */
    private List<Condition.Criterion> extractConditions(Object parameterObject) {
        List<Condition.Criterion> conditions = new ArrayList<>();
        if (parameterObject instanceof Map) {
            // 使用 IdentityHashMap 来跟踪已访问的对象，避免循环引用导致的无限循环
            Set<Object> visited = Collections.newSetFromMap(new IdentityHashMap<>());
            Map<?, ?> paramMap = (Map<?, ?>) parameterObject;
            for (Object value : paramMap.values()) {
                // 检查值是否是 Condition 并且之前没有访问过
                if (value instanceof Condition && visited.add(value)) {
                    List<Condition.Criterion> c = ((Condition) value).getConditionsFieldList();
                    if (c != null) {
                        conditions.addAll(c);
                    }
                }
            }
        } else {
            // 如果参数对象本身就是 Condition
            if (parameterObject instanceof Condition) {
                List<Condition.Criterion> c = ((Condition) parameterObject).getConditionsFieldList();
                if (c != null) {
                    conditions.addAll(c);
                }
            }
        }
        return conditions;
    }

    /**
     * 尝试通过解析 MappedStatement 的 ResultMap 来构建 property -> alias 映射。
     * 对于 column 名包含别名的显式 ResultMap (例如："t.column_name")，这是可靠的。
     * 对于 resultType 自动生成的 ResultMap，如果别名未保留在 column 名中，则不可靠。
     * 此方法无法超越 ResultMap 提供的信息进行推断。
     */
    private Map<String, String> buildFieldToAliasMap(MappedStatement ms) {
        // 此方法只处理 ResultMap 中的信息，不涉及 SQL 字符串解析。
        // 它不能“适应” ResultMap 未提供别名信息的场景，该适应逻辑在 processCriterion 中。

        String msId = ms.getId();
        // 尝试从缓存获取映射
        Map<String, String> cachedMap = msFieldAliasCache.get(msId);
        if (cachedMap != null) {
            return cachedMap;
        }

        Map<String, String> fieldAliasMap = new HashMap<>();
        List<ResultMap> resultMaps = ms.getResultMaps();

        if (resultMaps != null) {
            for (ResultMap resultMap : resultMaps) {
                List<ResultMapping> resultMappings = resultMap.getResultMappings();
                if (resultMappings != null) {
                    for (ResultMapping resultMapping : resultMappings) {
                        String property = resultMapping.getProperty(); // Java 属性名 (camelCase)
                        String column = resultMapping.getColumn(); // 数据库列名 (可能是 "alias.column_name" 或仅 "column_name")

                        if (isNotBlank(property) && isNotBlank(column)) {
                            // 如果 column 名包含 '.'，则假定点号前是别名
                            int dotIndex = column.indexOf('.');
                            if (dotIndex > 0) {
                                String alias = column.substring(0, dotIndex);
                                // 存储属性名 -> 别名映射
                                fieldAliasMap.putIfAbsent(property, alias);
                            }
                            // 注意：如果 ResultMap 的 column 在此处不包含别名，则我们无法从 ResultMap 中推断出它。
                        }
                    }
                }
            }
        }

        // 将构建好的映射存入缓存
        msFieldAliasCache.put(msId, fieldAliasMap);
        return fieldAliasMap;
    }

    /**
     * 处理单个 Criterion，生成 WHERE 或 ORDER BY 片段。
     * 尝试首先从 ResultMap 别名中查找别名，然后从 SQL 字符串搜索中查找。
     */
    private void processCriterion(Condition.Criterion criterion, List<String> whereConditions,
            List<Object> params, List<String> orderByConditions,
            Map<String, String> resultMapAliases, BoundSql boundSql) { // 接收 ResultMap 别名映射和 BoundSql
        String field = criterion.getField(); // 例如："companyName" (来自 Condition 的 camelCase 字段名)
        String operation = criterion.getOperation();
        Object value = criterion.getValue();
        String sortBy = criterion.getSortBy();

        // 如果字段名为空，则跳过此条件
        if (isBlank(field)) {
            return;
        }

        // 先将字段名转换为 snake_case，因为 SQL 搜索和数据库列名是 snake_case
        String mappedField = camelCaseToSnakeCase(field); // 例如："company_name"

        // 1. 尝试从 ResultMap 别名映射中获取别名（最可靠，如果 ResultMap 定义包含别名的话）
        // 使用原始字段名 (camelCase) 作为 ResultMap 别名映射的 key
        String tableAlias = resultMapAliases.get(field);

        // 2. 如果 ResultMap 中未找到别名，则尝试通过搜索 SQL 字符串来查找
        // 使用映射后的 (snake_case) 列名在原始 SQL 字符串中搜索。
        if (isBlank(tableAlias)) {
            tableAlias = findAliasInSql(boundSql.getSql(), mappedField);
            if (isBlank(tableAlias)) {
                return;
            }
        }

        // 如果找到了别名（无论来自 ResultMap 还是 SQL 搜索），则构建完整的列名（别名.列名）；否则只使用列名
        String fullColumnName = isNotBlank(tableAlias) ? tableAlias + "." + mappedField : mappedField;

        // 处理 ORDER BY 排序条件
        if (isNotBlank(sortBy)) {
            if ("asc".equalsIgnoreCase(sortBy) || "desc".equalsIgnoreCase(sortBy)) {
                orderByConditions.add(fullColumnName + " " + sortBy.toUpperCase());
            } else {
                log.info("不支持的排序方向: {}，字段: {}。跳过排序。", sortBy, field);
            }
        }

        // 处理 WHERE 过滤条件
        if (isNotBlank(operation)) {
            // 对于需要值的操作，如果值为 null，则跳过此条件
            if (value == null) {
                return;
            }

            // 将操作符转换为小写以便比较
            switch (operation.toLowerCase()) {
                case "eq": // 等于
                    whereConditions.add(fullColumnName + " = ?");
                    params.add(value);
                    break;
                case "ne": // 不等于
                    whereConditions.add(fullColumnName + " != ?");
                    params.add(value);
                    break;
                case "gt": // 大于
                    whereConditions.add(fullColumnName + " > ?");
                    params.add(value);
                    break;
                case "lt": // 小于
                    whereConditions.add(fullColumnName + " < ?");
                    params.add(value);
                    break;
                case "ge": // 大于等于
                    whereConditions.add(fullColumnName + " >= ?");
                    params.add(value);
                    break;
                case "le": // 小于等于
                    whereConditions.add(fullColumnName + " <= ?");
                    params.add(value);
                    break;
                case "like": // 模糊匹配
                    whereConditions.add(fullColumnName + " LIKE ?");
                    params.add("%" + value + "%"); // 默认使用 %value% 进行模糊匹配
                    break;
                // TODO: 根据需要添加更多操作符支持 (例如 'in', 'not in', 'between', 'is null', 'is not null'
                // 等)
                default:
                    log.info("不支持的操作符: {}，字段: {}。跳过 WHERE 条件。", operation, field);
                    break;
            }
        }
    }

    /**
     * 尝试通过搜索 SQL 字符串，为给定的 snake_case 列名查找表别名。
     * 这是一个使用正则表达式的尽力而为的方法，在所有复杂的 SQL 情况下可能不准确。
     * 它返回在列名之前找到的第一个别名，如果没有找到则返回 null。
     */
    private String findAliasInSql(String sql, String mappedColumnName) {
        // 如果 SQL 字符串或映射后的列名为空，则无法查找
        if (isBlank(sql) || isBlank(mappedColumnName)) {
            return null;
        }

        // 获取或编译此列名对应的正则表达式 Pattern
        // Pattern 查找 "别名." 后面跟着字面的列名，并在其周围有单词边界或字符串开头/结尾。
        // 它试图避免匹配列名本身（例如 "id.id"）或点号前的常见函数名。
        Pattern pattern = aliasColumnPatternCache.computeIfAbsent(mappedColumnName, key -> {
            String regex = String.format(ALIAS_COLUMN_REGEX_TEMPLATE, Pattern.quote(key));
            // 编译忽略大小写的 Pattern
            return Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        });

        Matcher matcher = pattern.matcher(sql);

        // 遍历匹配项，返回第一个位于顶层（括号深度为0，且不在引号内）的别名
        while (matcher.find()) {
            int matchStart = matcher.start();
            if (!isTopLevelPosition(sql, matchStart)) {
                continue;
            }
            String alias = matcher.group(1);
            if (isNotBlank(alias) && !alias.equalsIgnoreCase(mappedColumnName)) {
                return alias;
            }
        }

        // 如果没有找到匹配项或找到的匹配项有问题，则返回 null
        return null;
    }

    /**
     * 将 camelCase 字符串转换为 snake_case。
     * 例如："companyName" -> "company_name"
     * 例如："userID" -> "user_id" (处理连续大写)
     */
    private String camelCaseToSnakeCase(String camelCase) {
        if (camelCase == null || camelCase.isEmpty()) {
            return "";
        }

        StringBuilder snakeCase = new StringBuilder();
        // 第一个字符直接转小写
        snakeCase.append(Character.toLowerCase(camelCase.charAt(0)));

        for (int i = 1; i < camelCase.length(); i++) {
            char c = camelCase.charAt(i);
            if (Character.isUpperCase(c)) {
                // 如果当前字符是大写，且前一个字符不是下划线，则添加下划线
                // 同时处理连续大写的情况，如 "userID" -> "user_id"
                if (snakeCase.charAt(snakeCase.length() - 1) != '_') {
                    // 如果当前大写字母后面还有小写字母，或者这是最后一个字符，才加下划线
                    if ((i + 1 < camelCase.length() && Character.isLowerCase(camelCase.charAt(i + 1)))
                            || i == camelCase.length() - 1) {
                        snakeCase.append('_');
                    }
                }
                snakeCase.append(Character.toLowerCase(c));
            } else {
                snakeCase.append(c);
            }
        }

        // 移除末尾可能的多余下划线
        while (snakeCase.length() > 0 && snakeCase.charAt(snakeCase.length() - 1) == '_') {
            snakeCase.deleteCharAt(snakeCase.length() - 1);
        }

        return snakeCase.toString();
    }

    /**
     * 构建新的 SQL 字符串，插入 WHERE 和 ORDER BY 子句。
     * 此版本修复了 "WHERE AND" 问题，并替换现有的 ORDER BY 子句。
     * 根据主要的 SQL 子句（GROUP BY, HAVING, ORDER BY, LIMIT）确定插入位置。
     */
    private String buildNewSql(String originalSql, List<String> whereConditions, List<String> orderByConditions) {
        StringBuilder sql = new StringBuilder(originalSql);

        // 仅在顶层注入 WHERE 条件，避免进入子查询
        if (!whereConditions.isEmpty()) {
            String conditionString = String.join(" AND ", whereConditions);

            int wherePos = indexOfTopLevel(sql.toString(), "WHERE", 0);
            if (wherePos >= 0) {
                int afterWhere = wherePos + " WHERE ".length();
                int nextClause = indexOfFirstTopLevel(sql.toString(), afterWhere, "GROUP BY", "HAVING", "ORDER BY",
                        "LIMIT");
                int insertPos = nextClause >= 0 ? nextClause : sql.length();
                sql.insert(insertPos, " AND " + conditionString);
            } else {
                int firstTopLevel = indexOfFirstTopLevel(sql.toString(), 0, "WHERE", "GROUP BY", "HAVING", "ORDER BY",
                        "LIMIT");
                int insertPos = firstTopLevel >= 0 ? firstTopLevel : sql.length();
                sql.insert(insertPos, " WHERE " + conditionString);
            }
            // no-op
        }

        // 仅在顶层处理 ORDER BY（放在 LIMIT 之前），并替换顶层已有 ORDER BY
        if (!orderByConditions.isEmpty()) {
            String orderByClause = String.join(", ", orderByConditions);

            int existingOrderByStart = indexOfTopLevel(sql.toString(), "ORDER BY", 0);
            if (existingOrderByStart >= 0) {
                int afterExistingOrderBy = existingOrderByStart + " ORDER BY ".length();
                int endOfOrderBy = indexOfFirstTopLevel(sql.toString(), afterExistingOrderBy, "LIMIT");
                if (endOfOrderBy < 0) {
                    endOfOrderBy = sql.length();
                }
                sql.delete(existingOrderByStart, endOfOrderBy);
                // no-op
            }

            int limitPos = indexOfTopLevel(sql.toString(), "LIMIT", 0);
            int orderByInsertPos = limitPos >= 0 ? limitPos : sql.length();
            sql.insert(orderByInsertPos, " ORDER BY " + orderByClause);
        }

        return sql.toString().trim();
    }

    // 在顶层（括号深度为 0，且不在引号内）查找关键字首次出现的位置；关键字需以单词边界匹配，大小写不敏感
    private int indexOfTopLevel(String sql, String keyword, int fromIndex) {
        return findTopLevelKeyword(sql, keyword.toUpperCase(Locale.ROOT), fromIndex);
    }

    // 从 fromIndex 开始，查找若干关键字在顶层的最先出现位置（返回该位置，未找到返回 -1）
    private int indexOfFirstTopLevel(String sql, int fromIndex, String... keywords) {
        int minPos = -1;
        for (String kw : keywords) {
            int pos = findTopLevelKeyword(sql, kw.toUpperCase(Locale.ROOT), fromIndex);
            if (pos >= 0 && (minPos < 0 || pos < minPos)) {
                minPos = pos;
            }
        }
        return minPos;
    }

    private int findTopLevelKeyword(String sql, String keywordUpper, int fromIndex) {
        final String upper = sql.toUpperCase(Locale.ROOT);
        int depth = 0;
        boolean inSingle = false;
        boolean inDouble = false;
        for (int i = Math.max(0, fromIndex); i < upper.length(); i++) {
            char ch = upper.charAt(i);

            // 处理引号，尽量跳过字符串中的内容
            char raw = sql.charAt(i);
            if (!inDouble && raw == '\'') {
                // 处理 SQL 单引号（简单处理，未严格处理转义：对大部分业务 SQL 足够）
                inSingle = !inSingle;
                continue;
            }
            if (!inSingle && raw == '"') {
                inDouble = !inDouble;
                continue;
            }
            if (inSingle || inDouble) {
                continue;
            }

            // 括号深度
            if (ch == '(') {
                depth++;
                continue;
            } else if (ch == ')') {
                if (depth > 0) {
                    depth--;
                }
                continue;
            }

            if (depth == 0) {
                if (matchKeywordAt(upper, i, keywordUpper)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private boolean matchKeywordAt(String upperSql, int index, String keywordUpper) {
        int end = index + keywordUpper.length();
        if (end > upperSql.length()) {
            return false;
        }
        // 必须以关键字开头
        if (!upperSql.startsWith(keywordUpper, index)) {
            return false;
        }
        // 前后需要是边界（空白或字符串开头/结尾，或左括号）
        boolean leftBoundary = index == 0 || Character.isWhitespace(upperSql.charAt(index - 1))
                || upperSql.charAt(index - 1) == '(';
        boolean rightBoundary = end == upperSql.length() || Character.isWhitespace(upperSql.charAt(end))
                || upperSql.charAt(end) == ')';
        return leftBoundary && rightBoundary;
    }

    // 判断给定位置是否位于 SQL 顶层（括号深度为 0，且不在引号内）
    private boolean isTopLevelPosition(String sql, int position) {
        int depth = 0;
        boolean inSingle = false;
        boolean inDouble = false;
        for (int i = 0; i < position && i < sql.length(); i++) {
            char raw = sql.charAt(i);
            if (!inDouble && raw == '\'') {
                inSingle = !inSingle;
                continue;
            }
            if (!inSingle && raw == '"') {
                inDouble = !inDouble;
                continue;
            }
            if (inSingle || inDouble) {
                continue;
            }
            if (raw == '(') {
                depth++;
            } else if (raw == ')') {
                if (depth > 0) {
                    depth--;
                }
            }
        }
        return !inSingle && !inDouble && depth == 0;
    }

    /**
     * 重置 BoundSql 对象，更新 SQL 字符串并添加额外参数。
     */
    private void resetBoundSql(BoundSql boundSql, String newSql, Object parameter, List<Object> extraParams,
            Configuration configuration) {
        // 使用 MetaObject 来安全地修改 BoundSql 的内部属性
        MetaObject metaObject = SystemMetaObject.forObject(boundSql);

        // 更新 SQL 语句
        metaObject.setValue("sql", newSql);

        // 添加额外参数及其参数映射
        if (!extraParams.isEmpty()) {
            // 获取原始的参数映射列表
            List<ParameterMapping> oldMappings = boundSql.getParameterMappings();
            // 创建新的参数映射列表，并复制旧的映射
            List<ParameterMapping> newMappings = new ArrayList<>(oldMappings);

            // 为每个额外参数创建新的 ParameterMapping
            for (int i = 0; i < extraParams.size(); i++) {
                // 生成一个唯一的参数名称，避免与原始参数冲突
                String paramName = "conditionParam" + (newMappings.size() + i);

                // 构建 ParameterMapping 对象，指定参数名和 Java 类型（这里使用 Object.class）
                ParameterMapping.Builder builder = new ParameterMapping.Builder(configuration, paramName, Object.class);
                // 如果需要，可以在这里设置 JDBC 类型或其他配置

                // 将构建好的 ParameterMapping 添加到新的映射列表中
                newMappings.add(builder.build());

                // 将额外参数的实际值添加到 BoundSql 的 additionalParameters map 中
                // Mybatis 在参数绑定时会检查这个 map
                boundSql.setAdditionalParameter(paramName, extraParams.get(i));
            }

            // 使用新的包含额外参数的映射列表更新 BoundSql
            metaObject.setValue("parameterMappings", newMappings);
        }
    }

    // -------------------- 辅助方法 (替代 StringUtils.isBlank/isNotBlank)
    // --------------------

    private boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    private boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    // ---------------------------------------------------------------------------

    // 使用说明：
    // 1. 确保你的项目中有 Condition 和 Criterion 类，其结构如注释所示（Criterion 不包含 alias 字段）。
    // 2. 将 ConditionInterceptor 类放在你的项目中。
    // 3. 在 MyBatis 配置中注册此拦截器。如果你使用 Mybatis-Plus，通常通过 MybatisPlusInterceptor 添加
    // InnerInterceptor。
    // 如果你使用原生 MyBatis，则在 mybatis-config.xml 的 <plugins> 中配置。
    // 4. 在构建 Condition 对象和 Criterion 时，Criterion 不再需要指定表别名。
    // 例如：Condition.criterion("custName", "like", "测试")
    // Interceptor 将尝试通过解析 ResultMap 和搜索 SQL 字符串来自动查找 "custName" 对应的表别名。

    // 注意局限性：
    // - 自动别名推断（尤其是 SQL 搜索部分）是启发式的，不保证在所有复杂的 SQL 语法下都准确无误。
    // - 对于 resultType，如果 ResultMap 未包含别名信息，将完全依赖 SQL 字符串搜索，其准确性受限。
    // - 如果推断的别名不正确，可能导致 SQL 错误或查询结果不符预期。
    // - 在多表连接且列名不唯一的情况下，SQL 字符串搜索可能找到错误的别名（例如，在多个表中都存在 'id' 列）。

}