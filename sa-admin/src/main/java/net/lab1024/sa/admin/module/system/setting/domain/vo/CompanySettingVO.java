package net.lab1024.sa.admin.module.system.setting.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

/**
 * 系统参数设置-公司表 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-04-20 17:30:25
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class CompanySettingVO extends CompanyBaseEntity {


    @Schema(description = "系统参数设置 - 副表id")
    private Long companySettingId;

    @Schema(description = "系统参数设置主表id")
    private Long settingId;

    @Schema(description = "参数说明")
    private String paramDescription;

    @Schema(description = "参数值")
    private String paramValue;
}
