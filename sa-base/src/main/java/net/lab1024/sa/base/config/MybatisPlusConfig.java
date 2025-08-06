package net.lab1024.sa.base.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import jakarta.annotation.Resource;
import net.lab1024.sa.base.handler.ConditionInterceptor;
import net.lab1024.sa.base.handler.CustomTenantHandler;
import net.lab1024.sa.base.handler.StatisticInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mp 插件
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2021-09-02 20:21:10
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a>
 */
@EnableTransactionManagement
@Configuration
public class MybatisPlusConfig {

    @Resource
    private CustomTenantHandler customTenantHandler;

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 2. 按正确顺序添加拦截器
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor()); // 乐观锁
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(customTenantHandler)); // 多租户
        interceptor.addInnerInterceptor(new ConditionInterceptor()); // 自定义条件拦截器（必须在统计之前）
        interceptor.addInnerInterceptor(new StatisticInnerInterceptor()); // 统计拦截器（基于条件过滤后的数据）
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL)); // 分页（必须最后）
        return interceptor;
    }

}
