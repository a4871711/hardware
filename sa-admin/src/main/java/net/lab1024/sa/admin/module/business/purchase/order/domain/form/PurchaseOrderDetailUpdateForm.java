package net.lab1024.sa.admin.module.business.purchase.order.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.purchase.order.domain.entity.PurchaseOrderDetailEntity;

/**
 * 采购订货单详情 更新表单
 *
 * @Author 赵嘉伟
 * @Date 2025-07-08 22:10:24
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class PurchaseOrderDetailUpdateForm extends PurchaseOrderDetailEntity {

    @Schema(description = "采购订货单详情id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "采购订货单详情id 不能为空")
    private Long purchaseOrderDetailId;

}