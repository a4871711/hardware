package net.lab1024.sa.admin.module.business.purchase.request.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.json.serializer.FileKeyVoSerializer;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.LogBaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 采购申请单详情 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-06-23 22:34:08
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class PurchaseRequestOrderDetailVO extends LogBaseEntity {

    @Schema(description = "采购订货单详情id")
    private Long purchaseRequestOrderDetailId;

    @Schema(description = "采购订货单id")
    private Long purchaseRequestOrderId;

    @Schema(description = "销售订货单材料id")
    private Long salesOrderMatId;

    @Schema(description = "订单生产数量")
    private BigDecimal productionQty;

    @Schema(description = "订单物料用量")
    private BigDecimal usageAmount;

    @Schema(description = "采购数量")
    private BigDecimal purchaseQty;

    @Schema(description = "供应商")
    private Long custId;

    @Schema(description = "采购单价")
    private BigDecimal unitPurchase;

    @Schema(description = "金额(采购数量 * 采购单价)")
    private BigDecimal amount;

    @Schema(description = "已采购数量")
    private BigDecimal purchasedQty;

    @Schema(description = "可用库存")
    private BigDecimal availableStock;

    @Schema(description = "现有库存")
    private BigDecimal currentStock;

    @Schema(description = "物料状态（是否审核/不订购）")
    private Integer status;

    @Schema(description = "审核日期")
    private LocalDateTime auditTime;

    @Schema(description = "审核人")
    private Long auditUserId;

    @Schema(description = "审核人姓名")
    private String auditUserName;

    @Schema(description = "备注")
    private String remark;

    private Long matId;

    private String sourceProperty;

    /**
     * 物料编号
     */
    private String matNo;

    /**
     * 物料名称
     */
    private String matName;

    /**
     * 规格/型号
     */
    private String pro;

    /**
     * 颜色ID
     */
    private Long colorId;

    /**
     * 颜色名称
     */
    private String colorName;

    /**
     * BOM 产品编码
     */
    private String productCode;

    /**
     * BOM 产品名称
     */
    private String productName;

    /**
     * BOM 产品颜色
     */
    private String productColor;

    /**
     * 销售订单号
     */
    private String orderCode;

    /**
     * 供应商名称
     */
    private String custName;

    /**
     * 客户物料单价
     */
    private BigDecimal unitPrice;

    /**
     * 系列名称
     */
    private String seriesName;

    /**
     * 客户产品名称
     */
    private String custProductName;

    /**
     * 物料类型ID
     */
    private Long matTypeId;

    /**
     * 物料类型名称
     */
    private String matTypeName;

    /**
     * 物料类型编码
     */
    private String matTypeCode;

    /**
     * 物料图片
     */
    @JsonSerialize(using = FileKeyVoSerializer.class)
    private String matImage;

}
