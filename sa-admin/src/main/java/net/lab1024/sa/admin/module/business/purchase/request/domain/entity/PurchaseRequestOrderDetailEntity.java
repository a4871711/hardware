package net.lab1024.sa.admin.module.business.purchase.request.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.LogBaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 采购申请单详情 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-06-23 22:34:08
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_purchase_request_order_detail")
public class PurchaseRequestOrderDetailEntity extends LogBaseEntity {

    /**
     * 采购订货单详情id
     */
    @TableId
    private Long purchaseRequestOrderDetailId;

    /**
     * 采购订货单id
     */
    private Long purchaseRequestOrderId;

    /**
     * 销售订货单材料id
     */
    private Long salesOrderMatId;

    /**
     * 订单生产数量
     */
    private BigDecimal productionQty;

    /**
     * 订单物料用量
     */
    private BigDecimal usageAmount;

    /**
     * 采购数量
     */
    private BigDecimal purchaseQty;

    /**
     * 供应商
     */
    private Long custId;

    /**
     * 采购单价
     */
    private BigDecimal unitPurchase;

    /**
     * 金额(采购数量 * 采购单价)
     */
    private BigDecimal amount;

    /**
     * 已采购数量
     */
    private BigDecimal purchasedQty;

    /**
     * 可用库存
     */
    private BigDecimal availableStock;

    /**
     * 现有库存
     */
    private BigDecimal currentStock;

    /**
     * 物料状态（是否审核/不订购）
     */
    private Integer status;

    /**
     * 审核日期
     */
    private LocalDateTime auditTime;

    /**
     * 审核人
     */
    private Long auditUserId;

    /**
     * 审核人姓名
     */
    private String auditUserName;

    /**
     * 备注
     */
    private String remark;

    private Long matId;
}
