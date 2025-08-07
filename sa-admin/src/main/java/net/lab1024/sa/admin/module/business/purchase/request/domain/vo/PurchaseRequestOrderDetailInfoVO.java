package net.lab1024.sa.admin.module.business.purchase.request.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.math.BigDecimal;

/**
 * 采购申请单明细分页查询 VO
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PurchaseRequestOrderDetailInfoVO extends CompanyBaseEntity {

    @Schema(description = "采购申请单明细ID")
    private Long purchaseRequestOrderDetailId;

    @Schema(description = "采购申请单号")
    private String purchaseRequestOrderCode;

    @Schema(description = "销售订货单号")
    private String salesOrderCode;

    @Schema(description = "供应商ID")
    private Long custId;

    @Schema(description = "供应商名称")
    private String custName;

    @Schema(description = "产品编号")
    private String productCode;

    @Schema(description = "产品名称")
    private String productName;

    @Schema(description = "物料编号")
    private String matNo;

    @Schema(description = "物料名称")
    private String matName;

    @Schema(description = "订单物料用量")
    private BigDecimal usageAmount;
} 