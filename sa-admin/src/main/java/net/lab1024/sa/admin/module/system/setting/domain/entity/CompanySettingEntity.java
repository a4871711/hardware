package net.lab1024.sa.admin.module.system.setting.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

/**
 * 系统参数设置-公司表 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-04-20 17:30:25
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_company_setting")
public class CompanySettingEntity extends CompanyBaseEntity {

    /**
     * 系统参数设置 - 副表id
     */
    @TableId
    private Long companySettingId;


    private Long settingId;

    /**
     * 参数说明
     */
    private String paramDescription;

    /**
     * 参数值
     */
    private String paramValue;

    /**
     * 公司id(租户号)
     */
    private Long companyId;
}
