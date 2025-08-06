package net.lab1024.sa.admin.module.business.base.mat.matcust.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.math.BigDecimal;

/**
 * 物料供应商单价表 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-04-22 00:40:08
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_base_mat_cust")
public class BaseMatCustEntity extends CompanyBaseEntity {

    /**
     * 主键
     */
    @TableId
    private Long matCustId;

    /**
     * 物料id
     */
    private Long matId;

    /**
     * 供应商id
     */
    private Long custId;

    /**
     * 是否是主供应商
     */
    private Boolean master;

    /**
     * 供应商货号
     */
    private String custMatName;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 损耗
     */
    private BigDecimal wastePercent;

    /**
     * 损耗-个数
     */
    private BigDecimal wasteNum;

    /**
     * 备注
     */
    private String remark;

}
