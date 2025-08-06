package net.lab1024.sa.base.handler;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import net.lab1024.sa.base.common.exception.BusinessException;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.util.Map;
import java.util.Objects;

@Intercepts(
        {
                @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
                @Signature(type = StatementHandler.class, method = "getBoundSql", args = {}),
                @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
public class FwMybatisPlusInterceptor extends MybatisPlusInterceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final Object result = super.intercept(invocation);

        final Object target = invocation.getTarget();
        final Object[] args = invocation.getArgs();
        if (target instanceof Executor) {
            final Object parameter = args[1];
            MappedStatement ms = (MappedStatement) args[0];
            if (SqlCommandType.UPDATE == ms.getSqlCommandType()) {
                if (parameter instanceof Map) {
                    Map<String, Object> map = (Map<String, Object>) parameter;
                    if (map.containsKey(Constants.MP_OPTLOCK_VERSION_ORIGINAL)) {
                        if (result instanceof Number && Objects.equals(result, 0)) {
                            throw new BusinessException("数据已被其他用户修改，请刷新后重试！");
                        }
                    }
                }
            }
        }

        return result;
    }
}
