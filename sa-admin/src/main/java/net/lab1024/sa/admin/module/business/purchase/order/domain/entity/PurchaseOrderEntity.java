package net.lab1024.sa.admin.module.business.purchase.order.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 采购订货单 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-07-08 22:10:23
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_purchase_order")
public class PurchaseOrderEntity extends CompanyBaseEntity {

    /**
     * 采购订货单id
     */
    @TableId
    private Long purchaseOrderId;

    /**
     * 采购订货单号
     */
    private String purchaseOrderCode;

    /**
     * 下单日期
     */
    private LocalDateTime deliveryDate;

    /**
     * 收获日期
     */
    private LocalDateTime receiveDate;

    /**
     * 供应商
     */
    private Long custId;

    /**
     * 扣税类型
     */
    private String taxType;

    /**
     * 税点
     */
    private BigDecimal taxRate;

    /**
     * 收货地址
     */
    private String receiveAddress;

    /**
     * 备注
     */
    private String remark;

    private Boolean audit;

    private LocalDateTime auditTime;

    private String auditUserName;

    private Long auditUserId;

    private Boolean invalid;

    private LocalDateTime invalidTime;

    private String invalidUserName;

    private Long invalidUserId;

}
