package net.lab1024.sa.admin.module.business.base.bom.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.math.BigDecimal;

/**
 * 多级bom其他费用 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-04-30 21:05:57
 * @Copyright 赵嘉伟
 */

@Data
@TableName("t_base_bom_other_amount")
public class BaseBomOtherAmountEntity extends CompanyBaseEntity {

    /**
     * 主键
     */
    @TableId
    private Long bomOtherAmountId;

    /**
     * bom_id
     */
    private Long bomId;

    /**
     * 名称
     */
    private String otherAmountName;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 损耗
     */
    private BigDecimal waste;
}
