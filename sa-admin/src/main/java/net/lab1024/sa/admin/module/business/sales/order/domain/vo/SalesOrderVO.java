package net.lab1024.sa.admin.module.business.sales.order.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 销售订货单 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-05-25 19:36:32
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class SalesOrderVO extends CompanyBaseEntity {
    @Schema(description = "销售订货单id")
    private Long salesOrderId;

    @Schema(description = "订单号")
    private String orderCode;

    @Schema(description = "下单日期")
    private LocalDateTime orderTime;

    @Schema(description = "交货日期")
    private LocalDateTime deliveryTime;

    @Schema(description = "客户")
    private Long custId;

    @Schema(description = "客户名称")
    private String custName;

    @Schema(description = "送货地址")
    private String shippingAddress;

    @Schema(description = "扣税类型")
    private String taxType;

    @Schema(description = "税点")
    private BigDecimal taxRate;

    private String productionType;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "是否作废")
    private Boolean invalid;

    @Schema(description = "是否审核")
    private Boolean audit;

}
