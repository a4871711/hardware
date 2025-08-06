package net.lab1024.sa.base.component;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.base.common.tenant.TableMetaInfoHelper;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用来搜索表的元数据， 注意该bean需要依赖sqlSessionFactory（mybatis在该bean里初始化了所有表的元数据）
 *
 */
@Slf4j
@Component
@DependsOn("sqlSessionFactory")
public class TenantMetaInfoCollector implements SmartInitializingSingleton {

    @Override
    public void afterSingletonsInstantiated() {
        List<TableInfo> tableInfos = TableInfoHelper.getTableInfos();
        tableInfos.forEach(TableMetaInfoHelper::register);
    }

}
