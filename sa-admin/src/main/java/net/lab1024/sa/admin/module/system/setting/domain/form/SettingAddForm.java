package net.lab1024.sa.admin.module.system.setting.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.system.setting.domain.entity.SettingEntity;

/**
 * 系统参数设置-主表 新建表单
 *
 * @Author 赵嘉伟
 * @Date 2025-04-20 17:30:24
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class SettingAddForm extends SettingEntity {

    @Schema(description = "系统参数设置id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "系统参数设置id 不能为空")
    private Long settingId;


}