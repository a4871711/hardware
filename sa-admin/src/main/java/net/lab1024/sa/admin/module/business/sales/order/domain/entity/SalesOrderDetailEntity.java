package net.lab1024.sa.admin.module.business.sales.order.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 销售订货单明细表 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-05-25 19:36:41
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_sales_order_detail")
public class SalesOrderDetailEntity extends CompanyBaseEntity {

    /**
     * 销售订货单明细id
     */
    @TableId
    private Long salesOrderDetailId;

    /**
     * 销售订货单id
     */
    private Long salesOrderId;

    /**
     * 多级bom的id
     */
    private Long bomId;

    /**
     * 订单数量
     */
    private Integer orderQty;

    /**
     * 生产数量
     */
    private Integer productionQty;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 含税单价
     */
    private BigDecimal taxUnitPrice;

    /**
     * 税款
     */
    private BigDecimal taxAmount;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 价税合计
     */
    private BigDecimal taxTotalAmount;

    /**
     * 交货日期
     */
    private LocalDateTime deliveryTime;

    /**
     * 备注
     */
    private String remark;

}
