package net.lab1024.sa.admin.module.business.sales.order.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.math.BigDecimal;

/**
 * 销售订单原材料表 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-05-29 23:32:37
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class SalesOrderMatVO extends CompanyBaseEntity {


    @Schema(description = "主键")
    private Long salesOrderMatId;

    @Schema(description = "销售订货单id")
    private Long salesOrderId;

    @Schema(description = "销售订货单明细id")
    private Long bomId;

    @Schema(description = "物料id")
    private Long matId;

    @Schema(description = "物料编号")
    private String matNo;

    @Schema(description = "物料名称")
    private String matName;

    @Schema(description = "规格")
    private String pro;

    @Schema(description = "颜色")
    private String colorName;


    @Schema(description = "用量")
    private BigDecimal usageAmount;

    @Schema(description = "损耗")
    private BigDecimal waste;

    @Schema(description = "生产数量")
    private Integer productionQty;

    @Schema(description = "不含损耗用量")
    private BigDecimal totalUsageWithoutWaste;

    @Schema(description = "含损耗用量")
    private BigDecimal totalUsageWithWaste;

    @Schema(description = "来源属性")
    private String sourceProperty;

    @Schema(description = "产品编码")
    private String productCode;

    @Schema(description = "产品名称")
    private String productName;

    @Schema(description = "产品颜色")
    private String productColor;

    @Schema(description = "订单编码")
    private String orderCode;

    private Long custId;

    private String custName;

    private BigDecimal unitPrice;



}
