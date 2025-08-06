package net.lab1024.sa.admin.module.business.sales.order.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.math.BigDecimal;

/**
 * 销售订单原材料表 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-05-29 23:32:37
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_sales_order_mat")
public class SalesOrderMatEntity extends CompanyBaseEntity {

    /**
     * 主键
     */
    @TableId
    private Long salesOrderMatId;

    private Long bomId;

    /**
     * 物料id
     */
    private Long matId;

    private Long salesOrderId;

    /**
     * 用量
     */
    private BigDecimal usageAmount;

    /**
     * 损耗
     */
    private BigDecimal waste;

    /**
     * 生产数量
     */
    private Integer productionQty;

    /**
     * 不含损耗用量
     */
    private BigDecimal totalUsageWithoutWaste;

    /**
     * 含损耗用量
     */
    private BigDecimal totalUsageWithWaste;

}
