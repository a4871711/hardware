package net.lab1024.sa.admin.module.business.base.color.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 颜色资料表 新建表单
 *
 * @Author 赵嘉伟
 * @Date 2025-04-20 15:48:55
 * @Copyright 赵嘉伟
 */

@Data
public class BaseColorAddForm {

    @Schema(description = "颜色编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "颜色编号不能为空")
    private String colorCode;

    @Schema(description = "颜色名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "颜色名称不能为空")
    private String colorName;

    @Schema(description = "英文颜色", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "英文颜色不能为空")
    private String colorEngName;

    @Schema(description = "是否被禁用", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否被禁用不能为空")
    private Boolean disabledFlag;

    @Schema(description = "备注")
    private String remark;
}