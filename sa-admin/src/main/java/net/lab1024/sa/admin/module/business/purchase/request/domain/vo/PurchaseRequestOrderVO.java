package net.lab1024.sa.admin.module.business.purchase.request.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.LogBaseEntity;

import java.math.BigDecimal;

/**
 * 采购申请单 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-06-23 22:34:10
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class PurchaseRequestOrderVO extends LogBaseEntity {


    @Schema(description = "采购订货单id")
    private Long purchaseRequestOrderId;

    private String purchaseRequestOrderCode;

    private Boolean invalid;

    /**
     * 销售订单编号
     */
    @Schema(description = "销售订单编号")
    private String orderCode;

    /**
     * BOM 主键
     */
    @Schema(description = "BOM 主键")
    private Long bomId;

    /**
     * 产品名称
     */
    @Schema(description = "产品名称")
    private String productName;

    /**
     * 产品编码
     */
    @Schema(description = "产品编码")
    private String productCode;

    /**
     * 产品颜色
     */
    @Schema(description = "产品颜色")
    private String productColor;

    /**
     * 物料ID
     */
    @Schema(description = "物料ID")
    private Long matId;

    /**
     * 物料编号
     */
    @Schema(description = "物料编号")
    private String matNo;

    /**
     * 物料名称
     */
    @Schema(description = "物料名称")
    private String matName;

    /**
     * 规格/属性
     */
    @Schema(description = "规格/属性")
    private String pro;

    /**
     * 颜色ID
     */
    @Schema(description = "颜色ID")
    private Long colorId;

    /**
     * 颜色名称
     */
    @Schema(description = "颜色名称")
    private String colorName;

    /**
     * 状态（是否审核/不订购）
     */
    @Schema(description = "状态（是否审核/不订购）")
    private Integer status;

    /**
     * 订单生产数量
     */
    @Schema(description = "订单生产数量")
    private BigDecimal productionQty;

    /**
     * 订单物料用量
     */
    @Schema(description = "订单物料用量")
    private BigDecimal usageAmount;

    /**
     * 采购数量
     */
    @Schema(description = "采购数量")
    private BigDecimal purchaseQty;

    /**
     * 供应商ID
     */
    @Schema(description = "供应商ID")
    private Long custId;

    /**
     * 供应商名称
     */
    @Schema(description = "供应商名称")
    private String custName;

    /**
     * 采购单价
     */
    @Schema(description = "采购单价")
    private BigDecimal unitPurchase;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

}
