package net.lab1024.sa.admin.module.business.purchase.request.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.purchase.request.domain.entity.PurchaseRequestOrderDetailEntity;

/**
 * 采购申请单详情 更新表单
 *
 * @Author 赵嘉伟
 * @Date 2025-06-23 22:34:08
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class PurchaseRequestOrderDetailUpdateForm extends PurchaseRequestOrderDetailEntity {

    @Schema(description = "采购订货单详情id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "采购订货单详情id 不能为空")
    private Long purchaseRequestOrderDetailId;

}