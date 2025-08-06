package net.lab1024.sa.base.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.base.common.util.SmartRequestUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Mybatis Plus 插入或者更新时指定字段设置值
 *
 * @author zhoumingfa
 */
@Component
@Slf4j
public class MybatisPlusFillHandler implements MetaObjectHandler {

    public static final String CREATE_TIME = "createTime";

    public static final String CREATE_USER_ID = "createUserId";

    public static final String CREATE_USER_NAME = "createUserName";

    public static final String UPDATE_TIME = "updateTime";

    public static final String UPDATE_USER_ID = "updateUserId";

    public static final String UPDATE_USER_NAME = "updateUserName";

    public static final String COMPANY_ID = "companyId";

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasSetter(CREATE_TIME)) {
            this.setFieldValByName(CREATE_TIME, LocalDateTime.now(), metaObject);
        }
        if (metaObject.hasSetter(CREATE_USER_ID)) {
            this.setFieldValByName(CREATE_USER_ID, SmartRequestUtil.getRequestUserId(), metaObject);
        }
        if (metaObject.hasSetter(CREATE_USER_NAME)) {
            this.setFieldValByName(CREATE_USER_NAME, SmartRequestUtil.getRequestUserName(), metaObject);
        }

        if(metaObject.hasSetter(COMPANY_ID)) {
            this.setFieldValByName(COMPANY_ID, SmartRequestUtil.getRequestCompanyId(), metaObject);
        }

        // 插入时清空更新相关字段
        if (metaObject.hasSetter(UPDATE_TIME)) {
            this.setFieldValByName(UPDATE_TIME, null, metaObject);
        }
        if (metaObject.hasSetter(UPDATE_USER_ID)) {
            this.setFieldValByName(UPDATE_USER_ID, null, metaObject);
        }
        if (metaObject.hasSetter(UPDATE_USER_NAME)) {
            this.setFieldValByName(UPDATE_USER_NAME, null, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasSetter(UPDATE_TIME)) {
            this.setFieldValByName(UPDATE_TIME, LocalDateTime.now(), metaObject);
        }
        if (metaObject.hasSetter(UPDATE_USER_ID)) {
            this.setFieldValByName(UPDATE_USER_ID, SmartRequestUtil.getRequestUserId(), metaObject);
        }
        if (metaObject.hasSetter(UPDATE_USER_NAME)) {
            this.setFieldValByName(UPDATE_USER_NAME, SmartRequestUtil.getRequestUserName(), metaObject);
        }
    }

}
