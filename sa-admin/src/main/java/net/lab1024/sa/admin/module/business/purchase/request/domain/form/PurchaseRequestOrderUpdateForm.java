package net.lab1024.sa.admin.module.business.purchase.request.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.purchase.request.domain.entity.PurchaseRequestOrderEntity;

/**
 * 采购申请单 更新表单
 *
 * @Author 赵嘉伟
 * @Date 2025-06-23 22:34:10
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class PurchaseRequestOrderUpdateForm extends PurchaseRequestOrderEntity {

    @Schema(description = "采购订货单id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "采购订货单id 不能为空")
    private Long purchaseRequestOrderId;

}