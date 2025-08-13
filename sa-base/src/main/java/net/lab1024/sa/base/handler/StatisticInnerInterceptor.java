package net.lab1024.sa.base.handler;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CustomPage;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.StatisticalColumn;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SetOperationList;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 统计列求和的 Mybatis-Plus 内部拦截器。
 * 类似于 PaginationInnerInterceptor，它在主查询前执行一个独立的 SUM 查询。
 * <p>
 * 主要功能:
 * 1. 自动识别需要统计的字段
 * 2. 生成对应的SUM查询SQL
 * 3. 执行统计查询并将结果注入到参数对象中
 */
@Slf4j
@NoArgsConstructor
@SuppressWarnings({"rawtypes"})
public class StatisticInnerInterceptor implements InnerInterceptor {

    // 用于缓存动态生成的统计 MappedStatement
    protected static final Map<String, MappedStatement> statisticMsCache = new ConcurrentHashMap<>();
    // 别名查找相关的缓存和正则模板
    private static final String ALIAS_COLUMN_REGEX_TEMPLATE = "(?:^|\\W)([a-zA-Z0-9_]+)\\.%s(?:\\W|$)";
    protected final Log logger = LogFactory.getLog(this.getClass());
    private final Map<String, Pattern> aliasColumnPatternCache = new ConcurrentHashMap<>();
    private final Map<String, Map<String, String>> msFieldAliasCache = new ConcurrentHashMap<>();

    @Override
    public boolean willDoQuery(Executor executor, MappedStatement ms, Object parameter,
                               RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {

        // 仅处理 SELECT 操作
        if (!ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
            return true;
        }

        try {
            // 1. 提取统计列配置
            StatisticalColumn statisticalColumn = extractStatisticalColumn(parameter);
            List<String> statisticFields = getStatisticFields(statisticalColumn);

            if (statisticFields.isEmpty()) {
                return true; // 没有需要统计的字段，跳过统计逻辑
            }

            // 2. 执行统计查询
            Map<String, Object> statisticResults = executeStatisticQuery(
                    executor, ms, parameter, boundSql, statisticFields);

            // 3. 将统计结果设置回参数对象
            if (statisticResults != null && !statisticResults.isEmpty()) {
                // 同时尝试设置到Page对象（如果存在的话）
                setStatisticsToPageObject(parameter, statisticResults);
            }

        } catch (Exception e) {
            log.error("执行统计查询时发生错误", e);
            // 统计查询失败不应该影响主查询的执行
        }

        return true; // 继续执行主查询
    }

    /**
     * 从参数对象中提取 StatisticalColumn 实例
     * 支持从 Page 对象和 Map 参数中提取
     */
    protected StatisticalColumn extractStatisticalColumn(Object parameterObject) {
        if (parameterObject == null) {
            return null;
        }

        // 直接是 StatisticalColumn 类型
        if (parameterObject instanceof StatisticalColumn) {
            return (StatisticalColumn) parameterObject;
        }

        // Map 类型参数 (通常由 @Param 注解产生)
        if (parameterObject instanceof Map) {
            Map<?, ?> paramMap = (Map<?, ?>) parameterObject;
            for (Object value : paramMap.values()) {
                StatisticalColumn column = extractStatisticalColumn(value);
                if (column != null) {
                    return column;
                }
            }
        }

        return null;
    }

    /**
     * 获取需要统计的字段列表
     */
    private List<String> getStatisticFields(StatisticalColumn statisticalColumn) {
        if (statisticalColumn == null ||
                CollectionUtils.isEmpty(statisticalColumn.getStatisticFieldList())) {
            return Collections.emptyList();
        }
        return statisticalColumn.getStatisticFieldList();
    }

    /**
     * 执行统计查询
     */
    private Map<String, Object> executeStatisticQuery(Executor executor, MappedStatement ms,
                                                      Object parameter, BoundSql boundSql, List<String> statisticFields) throws SQLException {

        // 使用修改后的SQL（包含ConditionInterceptor添加的条件）
        String originalSql = boundSql.getSql();

        // 构建SUM表达式
        List<String> sumExpressions = buildSumExpressions(ms, originalSql, statisticFields);
        if (sumExpressions.isEmpty()) {
            log.warn("无法为字段 {} 构建有效的SUM表达式", statisticFields);
            return Collections.emptyMap();
        }

        // 生成统计SQL
        String statisticSql = buildStatisticSql(originalSql, sumExpressions);
        if (statisticSql == null) {
            log.warn("无法基于原始SQL构建统计查询: {}", originalSql);
            return Collections.emptyMap();
        }

        log.debug("原始SQL: {}", originalSql);
        log.debug("统计SQL: {}", statisticSql);

        try {
            // 使用BoundSql中的参数映射（包含ConditionInterceptor添加的参数映射）
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

            // 创建统计查询的MappedStatement
            MappedStatement statisticMs = buildStatisticMappedStatement(ms, statisticSql, parameterMappings);

            // 创建包含所有参数的新BoundSql，包括additionalParameters
            BoundSql statisticBoundSql = createStatisticBoundSql(statisticMs, parameter, boundSql);

            // 执行统计查询
            List<Map<String, Object>> queryResults = executor.query(
                    statisticMs, parameter, RowBounds.DEFAULT, null, null, statisticBoundSql);

            // 处理查询结果
            return processStatisticResults(statisticFields, queryResults);

        } catch (Exception e) {
            log.warn("统计查询执行失败，可能是参数映射问题: {}", e.getMessage());
            log.debug("统计查询详细错误信息", e);

            // 尝试使用无参数的统计查询作为降级方案
            return executeStatisticQueryWithoutParams(executor, ms, statisticSql, statisticFields);
        }
    }

    /**
     * 创建统计查询的BoundSql，确保包含所有必要的参数
     */
    private BoundSql createStatisticBoundSql(MappedStatement statisticMs, Object parameter, BoundSql originalBoundSql) {
        BoundSql statisticBoundSql = statisticMs.getBoundSql(parameter);

        // 复制原始BoundSql中的additionalParameters到统计BoundSql中
        // 这些参数包含了ConditionInterceptor添加的额外参数
        Map<String, Object> additionalParameters = originalBoundSql.getAdditionalParameters();
        if (additionalParameters != null && !additionalParameters.isEmpty()) {
            for (Map.Entry<String, Object> entry : additionalParameters.entrySet()) {
                statisticBoundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
            }
        }

        return statisticBoundSql;
    }

    /**
     * 执行无参数的统计查询（降级方案）
     */
    private Map<String, Object> executeStatisticQueryWithoutParams(Executor executor, MappedStatement ms,
                                                                   String statisticSql, List<String> statisticFields) {
        try {
            log.debug("尝试执行无参数统计查询作为降级方案");

            // 创建无参数的MappedStatement
            MappedStatement statisticMs = buildStatisticMappedStatement(ms, statisticSql, Collections.emptyList());

            // 执行统计查询（无参数）
            BoundSql statisticBoundSql = statisticMs.getBoundSql(null);
            List<Map<String, Object>> queryResults = executor.query(
                    statisticMs, null, RowBounds.DEFAULT, null, null, statisticBoundSql);

            return processStatisticResults(statisticFields, queryResults);

        } catch (Exception e) {
            log.warn("无参数统计查询也失败: {}", e.getMessage());
            return Collections.emptyMap();
        }
    }

    /**
     * 构建SUM表达式列表
     */
    private List<String> buildSumExpressions(MappedStatement ms, String originalSql, List<String> statisticFields) {
        Map<String, String> resultMapAliases = buildFieldToAliasMap(ms);
        List<String> sumExpressions = new ArrayList<>();

        for (String field : statisticFields) {
            if (StringUtils.isBlank(field)) {
                continue;
            }

            String columnName = camelCaseToSnakeCase(field);
            String tableAlias = getTableAlias(resultMapAliases, originalSql, field, columnName);

            String fullColumnName = StringUtils.isNotBlank(tableAlias)
                    ? tableAlias + Constants.DOT + columnName
                    : columnName;

            // 使用更清晰的别名命名
            String aliasName = columnName + "_sum";
            sumExpressions.add(String.format("SUM(%s) AS %s", fullColumnName, aliasName));
        }

        return sumExpressions;
    }

    /**
     * 获取表别名
     */
    private String getTableAlias(Map<String, String> resultMapAliases, String originalSql,
                                 String field, String columnName) {
        // 优先从ResultMap中获取别名
        String tableAlias = resultMapAliases.get(field);
        if (StringUtils.isNotBlank(tableAlias)) {
            return tableAlias;
        }

        // 从SQL中查找别名
        return findAliasInSql(originalSql, columnName);
    }

    /**
     * 使用 JSQLParser 构建统计 SQL
     */
    protected String buildStatisticSql(String originalSql, List<String> sumExpressions) {
        try {
            Select select = (Select) CCJSqlParserUtil.parse(originalSql);

            // 处理复杂查询(UNION等)，包装为子查询
            if (select instanceof SetOperationList) {
                String sumClause = String.join(", ", sumExpressions);
                return String.format("SELECT %s FROM (%s) AS temp_table", sumClause, originalSql);
            }

            // 处理普通SELECT查询
            if (select instanceof PlainSelect) {
                return buildPlainSelectStatisticSql((PlainSelect) select, sumExpressions);
            }

            return null;

        } catch (JSQLParserException e) {
            log.warn("JSQLParser解析SQL失败: {}", originalSql, e);
            return null;
        } catch (Exception e) {
            log.error("构建统计SQL时发生错误: {}", originalSql, e);
            return null;
        }
    }

    /**
     * 构建PlainSelect的统计SQL
     */
    private String buildPlainSelectStatisticSql(PlainSelect plainSelect, List<String> sumExpressions) {
        try {
            // 克隆对象避免修改原始查询
            PlainSelect clonedSelect = new PlainSelect();

            // 复制必要的部分
            clonedSelect.setFromItem(plainSelect.getFromItem());
            clonedSelect.setJoins(plainSelect.getJoins());
            clonedSelect.setWhere(plainSelect.getWhere());
            clonedSelect.setGroupByElement(plainSelect.getGroupBy());
            clonedSelect.setHaving(plainSelect.getHaving());

            // 设置新的SELECT项 - SUM表达式
            List<SelectItem<?>> newSelectItems = sumExpressions.stream()
                    .map(this::parseSelectItem)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            if (newSelectItems.isEmpty()) {
                return null;
            }

            clonedSelect.setSelectItems(newSelectItems);

            // 移除不需要的子句
            clonedSelect.setOrderByElements(null); // SUM查询不需要排序
            clonedSelect.setLimit(null); // SUM查询不需要限制
            clonedSelect.setOffset(null); // SUM查询不需要偏移
            clonedSelect.setDistinct(null); // SUM查询不需要去重

            return clonedSelect.toString();

        } catch (Exception e) {
            log.error("构建PlainSelect统计SQL失败", e);
            return null;
        }
    }

    /**
     * 解析单个SELECT项
     */
    private SelectItem<?> parseSelectItem(String expression) {
        try {
            String testSql = "SELECT " + expression;
            Select select = (Select) CCJSqlParserUtil.parse(testSql);

            if (select instanceof PlainSelect) {
                PlainSelect plainSelect = (PlainSelect) select;
                List<SelectItem<?>> items = plainSelect.getSelectItems();
                if (!items.isEmpty()) {
                    return items.get(0);
                }
            }
        } catch (JSQLParserException e) {
            log.error("解析SELECT表达式失败: {}", expression, e);
        }
        return null;
    }

    /**
     * 构建用于统计查询的 MappedStatement
     */
    protected MappedStatement buildStatisticMappedStatement(MappedStatement ms, String statisticSql,
                                                            List<ParameterMapping> parameterMappings) {

        final String statisticId = ms.getId() + "_mpSum";
        final Configuration configuration = ms.getConfiguration();

        return CollectionUtils.computeIfAbsent(statisticMsCache, statisticId, key -> {
            MappedStatement.Builder builder = new MappedStatement.Builder(
                    configuration,
                    key,
                    new StaticSqlSource(configuration, statisticSql, parameterMappings),
                    SqlCommandType.SELECT);

            // 复制原始MappedStatement的基本配置
            builder.resource(ms.getResource());
            builder.fetchSize(ms.getFetchSize());
            builder.statementType(ms.getStatementType());
            builder.timeout(ms.getTimeout());
            builder.parameterMap(ms.getParameterMap());
            builder.resultSetType(ms.getResultSetType());
            builder.flushCacheRequired(ms.isFlushCacheRequired());

            // 创建ResultMap - 映射到Map<String, Object>
            List<ResultMap> resultMaps = Collections.singletonList(
                    new ResultMap.Builder(
                            configuration,
                            key + "_StatisticResultMap",
                            Map.class,
                            new ArrayList<>()).build());
            builder.resultMaps(resultMaps);

            // 禁用缓存 - 统计查询应该总是获取最新数据
            builder.cache(null);
            builder.useCache(false);

            return builder.build();
        });
    }

    /**
     * 处理统计查询结果
     */
    protected Map<String, Object> processStatisticResults(List<String> statisticFields,
                                                          List<Map<String, Object>> statisticResults) {

        if (CollectionUtils.isEmpty(statisticResults)) {
            log.debug("统计查询无结果，返回空Map");
            return new HashMap<>();
        }

        Map<String, Object> finalStatistics = new HashMap<>();
        Map<String, Object> rawResult = statisticResults.get(0);

        for (String field : statisticFields) {
            String sumKey = camelCaseToSnakeCase(field) + "_sum";
            Object sumValue = rawResult.get(sumKey);

            if (sumValue != null) {
                finalStatistics.put(field, sumValue);
            } else {
                finalStatistics.put(field, 0); // 默认为0而不是null
                log.debug("字段 {} 的统计结果为空，设置默认值0", field);
            }
        }

        return finalStatistics;
    }

    // ==================== 辅助方法 ====================

    /**
     * 构建字段到别名的映射关系
     */
    private Map<String, String> buildFieldToAliasMap(MappedStatement ms) {
        String msId = ms.getId();
        return msFieldAliasCache.computeIfAbsent(msId, key -> {
            Map<String, String> fieldAliasMap = new HashMap<>();

            List<ResultMap> resultMaps = ms.getResultMaps();
            if (resultMaps != null) {
                for (ResultMap resultMap : resultMaps) {
                    extractAliasFromResultMap(resultMap, fieldAliasMap);
                }
            }

            return fieldAliasMap;
        });
    }

    /**
     * 从ResultMap中提取别名信息
     */
    private void extractAliasFromResultMap(ResultMap resultMap, Map<String, String> fieldAliasMap) {
        List<ResultMapping> resultMappings = resultMap.getResultMappings();
        if (resultMappings == null) {
            return;
        }

        for (ResultMapping resultMapping : resultMappings) {
            String property = resultMapping.getProperty();
            String column = resultMapping.getColumn();

            if (StringUtils.isNotBlank(property) && StringUtils.isNotBlank(column)) {
                int dotIndex = column.indexOf(Constants.DOT);
                if (dotIndex > 0) {
                    String alias = column.substring(0, dotIndex);
                    fieldAliasMap.putIfAbsent(property, alias);
                }
            }
        }
    }

    /**
     * 在SQL中查找字段对应的表别名
     */
    private String findAliasInSql(String sql, String columnName) {
        if (StringUtils.isBlank(sql) || StringUtils.isBlank(columnName)) {
            return null;
        }

        Pattern pattern = aliasColumnPatternCache.computeIfAbsent(columnName, key -> {
            String regex = String.format(ALIAS_COLUMN_REGEX_TEMPLATE, Pattern.quote(key));
            return Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        });

        Matcher matcher = pattern.matcher(sql);
        if (matcher.find()) {
            String alias = matcher.group(1);
            if (StringUtils.isNotBlank(alias) && !alias.equalsIgnoreCase(columnName)) {
                return alias;
            }
        }

        return null;
    }

    /**
     * 驼峰命名转下划线命名
     * 优化了转换逻辑，处理连续大写字母的情况
     */
    private String camelCaseToSnakeCase(String camelCase) {
        if (StringUtils.isBlank(camelCase)) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        char[] chars = camelCase.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char current = chars[i];

            if (Character.isUpperCase(current)) {
                // 不是第一个字符且前一个字符不是下划线时添加下划线
                if (i > 0 && result.length() > 0 && result.charAt(result.length() - 1) != '_') {
                    // 处理连续大写字母的情况 (如: XMLHttpRequest -> xml_http_request)
                    if (i + 1 < chars.length && Character.isLowerCase(chars[i + 1])) {
                        result.append('_');
                    }
                }
                result.append(Character.toLowerCase(current));
            } else {
                result.append(current);
            }
        }

        return result.toString();
    }

    /**
     * 尝试将统计结果设置到Page对象中
     */
    private void setStatisticsToPageObject(Object parameter, Map<String, Object> statisticResults) {
        if (parameter == null) {
            return;
        }

        // 直接是Page对象
        if (parameter instanceof IPage) {
            setStatisticsToPage((IPage<?>) parameter, statisticResults);
            return;
        }

        // Map类型参数，查找其中的Page对象
        if (parameter instanceof Map) {
            Map<?, ?> paramMap = (Map<?, ?>) parameter;
            for (Object value : paramMap.values()) {
                if (value instanceof IPage) {
                    setStatisticsToPage((IPage<?>) value, statisticResults);
                    return;
                }
            }
        }
    }

    /**
     * 将统计结果设置到Page对象中
     */
    private void setStatisticsToPage(IPage<?> page,
                                     Map<String, Object> statisticResults) {
        // 直接判断是否是CustomPage类型
        if (page instanceof CustomPage) {
            CustomPage<?> customPage = (CustomPage<?>) page;
            customPage.setStatisticsResultMap(statisticResults);
            log.debug("Statistical results set to CustomPage: {}", statisticResults);
        } else {
            log.debug("Page object is not CustomPage, skip setting statistics");
        }
    }
}