package net.lab1024.sa.admin.module.business.base.bom.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 多级bom其他费用 更新表单
 *
 * @Author 赵嘉伟
 * @Date 2025-04-30 21:05:57
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseBomOtherAmountUpdateForm extends BaseBomOtherAmountAddForm {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "主键 不能为空")
    private Long bomOtherAmountId;

    @Schema(description = "版本号(乐观锁)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "版本号(乐观锁) 不能为空")
    private Integer version;

}