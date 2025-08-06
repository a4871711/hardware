package net.lab1024.sa.admin.module.business.purchase.request.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.purchase.order.domain.vo.PurchaseOrderVO;

/**
 * 采购申请单被采购订货单引用 结果VO
 *
 * @author 赵嘉伟
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PurchaseOrderReferenceVO extends PurchaseOrderVO {

    @Schema(description = "采购申请单ID")
    private Long purchaseRequestOrderId;

    @Schema(description = "采购申请单号")
    private String purchaseRequestOrderCode;
}
