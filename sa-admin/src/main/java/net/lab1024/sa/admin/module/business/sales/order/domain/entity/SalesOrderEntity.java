package net.lab1024.sa.admin.module.business.sales.order.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 销售订货单 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-05-25 19:36:32
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_sales_order")
public class SalesOrderEntity extends CompanyBaseEntity {

    /**
     * 销售订货单id
     */
    @TableId
    private Long salesOrderId;

    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 下单日期
     */
    private LocalDateTime orderTime;

    /**
     * 交货日期
     */
    private LocalDateTime deliveryTime;

    /**
     * 客户
     */
    private Long custId;

    /**
     * 送货地址
     */
    private String shippingAddress;

    /**
     * 扣税类型
     */
    private String taxType;

    /**
     * 税点
     */
    private BigDecimal taxRate;

    /**
     * 生产类型
     */
    private String productionType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否作废
     */
    private Boolean invalid;

    /**
     * 是否审核
     */
    private Boolean audit;

}
