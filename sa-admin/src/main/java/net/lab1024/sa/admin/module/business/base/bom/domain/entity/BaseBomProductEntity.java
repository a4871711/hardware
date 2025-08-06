package net.lab1024.sa.admin.module.business.base.bom.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.math.BigDecimal;

/**
 * 多级BOM-产品材料 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-04-30 21:05:57
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_base_bom_product")
public class BaseBomProductEntity extends CompanyBaseEntity {

    /**
     * 多级bom-产品材料id
     */
    @TableId
    private Long bomProductId;

    /**
     * bom-id
     */
    private Long bomId;

    /**
     * 物料编号
     */
    private Long matId;

    /**
     * 父级物料id
     */
    private Long parentMatId;

    /**
     * 用量
     */
    private BigDecimal usageAmount;

    /**
     * 损耗
     */
    private BigDecimal waste;

    /**
     * 供应商
     */
    private Long custId;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

}
