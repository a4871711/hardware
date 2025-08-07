package net.lab1024.sa.admin.module.business.purchase.order.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 采购订货单 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-07-08 22:10:23
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class PurchaseOrderVO extends CompanyBaseEntity {


    @Schema(description = "采购订货单ID")
    private Long purchaseOrderId;

    @Schema(description = "采购订货单号")
    private String purchaseOrderCode;

    @Schema(description = "下单日期")
    private LocalDateTime deliveryDate;

    @Schema(description = "收获日期")
    private LocalDateTime receiveDate;

    @Schema(description = "供应商")
    private Long custId;

    @Schema(description = "扣税类型")
    private String taxType;

    @Schema(description = "税点")
    private BigDecimal taxRate;

    @Schema(description = "收货地址")
    private String receiveAddress;

    @Schema(description = "备注")
    private String remark;

    private String purchaseRequestOrderCode;

    private Boolean audit;

    private LocalDateTime auditTime;

    private String auditUserName;

    private Long auditUserId;

    private Boolean invalid;

    private LocalDateTime invalidTime;

    private String invalidUserName;

    private Long invalidUserId;

}
