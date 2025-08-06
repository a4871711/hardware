package net.lab1024.sa.admin.module.business.base.mat.matcust.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.math.BigDecimal;

/**
 * 物料供应商单价表 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-04-22 00:40:08
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseMatCustVO extends CompanyBaseEntity {


    @Schema(description = "主键")
    private Long matCustId;

    @Schema(description = "物料id")
    private Long matId;

    @Schema(description = "供应商id")
    private Long custId;

    @Schema(description = "是否是主供应商")
    private Boolean master;

    @Schema(description = "供应商货号")
    private String custMatName;

    @Schema(description = "单价")
    private BigDecimal price;

    @Schema(description = "损耗-百分比")
    private BigDecimal wastePercent;

    @Schema(description = "损耗-个数")
    private BigDecimal wasteNum;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "客商名称")
    private String custName;

}
