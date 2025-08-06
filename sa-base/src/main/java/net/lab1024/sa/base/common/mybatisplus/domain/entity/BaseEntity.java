package net.lab1024.sa.base.common.mybatisplus.domain.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serializable;


@Data
public class BaseEntity implements Serializable {

    /**
     * 删除状态
     */
    public static final String DELETED_FLAG = "deletedFlag";
    /**
     * 版本
     */
    public static final String VERSION = "version";
    /**
     * 创建时间
     */
    public static final String CREATE_TIME = "createTime";
    /**
     * 创建员工ID
     */
    public static final String CREATE_USER_ID = "createUserId";
    /**
     * 创建员工姓名
     */
    public static final String CREATE_USER_NAME = "createUserName";
    /**
     * 修改时间
     */
    public static final String UPDATE_TIME = "updateTime";
    /**
     * 修改员工姓名
     */
    public static final String UPDATE_USER_NAME = "updateUserName";
    /**
     * 修改员工ID
     */
    public static final String UPDATE_USER_ID = "updateUserId";

    /**
     * 审核时间
     */
    public static final String PASS_TIME = "passTime";
    /**
     * 审核员工姓名
     */
    public static final String PASS_USER_NAME = "passUserName";
    /**
     * 审核员工ID
     */
    public static final String PASS_USER_ID = "passUserId";
    /**
     * 公司ID
     */
    public static final String COMPANY_ID = "companyId";

    /**
     * 复制时不复制的列
     */
    public static final String[] COPY_EXCLUDED_PROPS = new String[]{
            DELETED_FLAG, VERSION, CREATE_TIME, CREATE_USER_ID, CREATE_USER_NAME,
            UPDATE_TIME, UPDATE_USER_NAME, UPDATE_USER_ID, PASS_TIME,
            PASS_USER_NAME, PASS_USER_ID, COMPANY_ID
    };

    /**
     * 已删除
     */
    @TableLogic
    private Boolean deletedFlag;

    /**
     * 版本
     */
    @Version
    private Integer version;
}
