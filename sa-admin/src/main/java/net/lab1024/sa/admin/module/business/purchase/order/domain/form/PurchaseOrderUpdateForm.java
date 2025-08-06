package net.lab1024.sa.admin.module.business.purchase.order.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.purchase.order.domain.entity.PurchaseOrderEntity;

/**
 * 采购订货单 更新表单
 *
 * @Author 赵嘉伟
 * @Date 2025-07-08 22:10:23
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class PurchaseOrderUpdateForm extends PurchaseOrderEntity {

    @Schema(description = "采购订货单id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "采购订货单id 不能为空")
    private Long purchaseOrderId;

}