package net.lab1024.sa.admin.module.system.setting.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.system.setting.domain.entity.CompanySettingEntity;

/**
 * 系统参数设置-公司表 新建表单
 *
 * @Author 赵嘉伟
 * @Date 2025-04-20 17:30:25
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class CompanySettingAddForm extends CompanySettingEntity {

    @Schema(description = "系统参数设置 - 副表id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "系统参数设置 - 副表id 不能为空")
    private Long companySettingId;

    @Schema(description = "系统参数设置主表id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "系统参数设置主表id 不能为空")
    private Long settingId;

    @Schema(description = "公司id(租户号)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "公司id(租户号) 不能为空")
    private Long companyId;

    @Schema(description = "版本号(乐观锁)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "版本号(乐观锁) 不能为空")
    private Integer version;

}