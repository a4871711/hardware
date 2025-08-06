package net.lab1024.sa.admin.module.business.sales.order.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.json.serializer.FileKeyVoSerializer;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 销售订货单明细表 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-05-25 19:36:41
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class SalesOrderDetailVO extends CompanyBaseEntity {


    @Schema(description = "销售订货单明细id")
    private Long salesOrderDetailId;

    @Schema(description = "销售订货单id")
    private Long salesOrderId;

    @Schema(description = "多级bom的id")
    private Long bomId;

    @Schema(description = "订单数量")
    private Integer orderQty;

    @Schema(description = "生产数量")
    private Integer productionQty;

    @Schema(description = "单价")
    private BigDecimal unitPrice;

    @Schema(description = "含税单价")
    private BigDecimal taxUnitPrice;

    @Schema(description = "金额")
    private BigDecimal amount;

    @Schema(description = "税款")
    private BigDecimal taxAmount;

    @Schema(description = "价税合计")
    private BigDecimal taxTotalAmount;

    @Schema(description = "交货日期")
    private LocalDateTime deliveryTime;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "产品编号")
    private String productCode;

    @Schema(description = "产品名称")
    private String productName;

    @Schema(description = "客户产品编号")
    private String custProductCode;

    @Schema(description = "客户产品名称")
    private String custProductName;

    @Schema(description = "系列")
    private String seriesName;

    @Schema(description = "产品颜色")
    private String productColor;

    @Schema(description = "产品英文颜色")
    private String productEngColor;

    @Schema(description = "产品图片")
    @JsonSerialize(using = FileKeyVoSerializer.class)
    private String productImage;
}
