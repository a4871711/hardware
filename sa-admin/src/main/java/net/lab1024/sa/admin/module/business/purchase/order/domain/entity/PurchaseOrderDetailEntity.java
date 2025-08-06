package net.lab1024.sa.admin.module.business.purchase.order.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.math.BigDecimal;

/**
 * 采购订货单详情 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-07-08 22:10:24
 * @Copyright 赵嘉伟
 */

@Data
@TableName("t_purchase_order_detail")
public class PurchaseOrderDetailEntity extends CompanyBaseEntity {

    /**
     * 采购订货单详情id
     */
    @TableId
    private Long purchaseOrderDetailId;

    /**
     * 申购单号（采购申请单订单号）
     */
    private Long purchaseRequestOrderDetailId;

    private Long purchaseOrderId;

    /**
     * 销售订货单材料id
     */
    private Long salesOrderMatId;

    /**
     * 数量
     */
    private BigDecimal orderQty;

    /**
     * 单价
     */
    private BigDecimal purchasePrice;

    /**
     * 含税单价
     */
    private BigDecimal taxPurchasePrice;

    /**
     * 税款
     */
    private BigDecimal taxAmount;

    /**
     * 货款
     */
    private BigDecimal amount;

    /**
     * 价税合计
     */
    private BigDecimal taxTotalAmount;

    /**
     * 备注
     */
    private String remark;
}
