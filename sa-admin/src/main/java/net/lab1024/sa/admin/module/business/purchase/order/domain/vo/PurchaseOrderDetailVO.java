package net.lab1024.sa.admin.module.business.purchase.order.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.math.BigDecimal;

/**
 * 采购订货单详情 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-07-08 22:10:24
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class PurchaseOrderDetailVO extends CompanyBaseEntity {


    @Schema(description = "采购订货单详情id")
    private Long purchaseOrderDetailId;

    private Long purchaseRequestOrderDetailId;

    private Long purchaseOrderId;

    @Schema(description = "销售订货单材料id")
    private Long salesOrderMatId;

    @Schema(description = "数量")
    private BigDecimal orderQty;

    @Schema(description = "单价")
    private BigDecimal purchasePrice;

    @Schema(description = "含税单价")
    private BigDecimal taxPurchasePrice;

    @Schema(description = "税款")
    private BigDecimal taxAmount;

    @Schema(description = "货款")
    private BigDecimal amount;

    @Schema(description = "价税合计")
    private BigDecimal taxTotalAmount;

    @Schema(description = "备注")
    private String remark;


    @Schema(description = "采购申请单号")
    private String purchaseRequestOrderCode;

    @Schema(description = "销售订单号")
    private String salesOrderCode;

    @Schema(description = "客户id")
    private Long custId;

    @Schema(description = "客户名称")
    private String custName;

    @Schema(description = "产品编码")
    private String productCode;

    @Schema(description = "产品名称")
    private String productName;

    @Schema(description = "材料id")
    private Long matId;

    @Schema(description = "材料编号")
    private String matNo;

}
