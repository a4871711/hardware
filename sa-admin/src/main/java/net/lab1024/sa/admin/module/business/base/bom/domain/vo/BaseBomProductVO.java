package net.lab1024.sa.admin.module.business.base.bom.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * 多级BOM-产品材料 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-04-30 21:05:57
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseBomProductVO extends CompanyBaseEntity {


    @Schema(description = "多级bom-产品材料id")
    private Long bomProductId;

    @Schema(description = "bom-id")
    private Long bomId;

    @Schema(description = "物料编号")
    private Long matId;

    @Schema(description = "父级物料id")
    private Long parentMatId;

    @Schema(description = "用量")
    private BigDecimal usageAmount;

    @Schema(description = "损耗")
    private BigDecimal waste;

    @Schema(description = "客商名称")
    private Long custId;

    @Schema(description = "单价")
    private BigDecimal unitPrice;

    @Schema(description = "物料名称")
    private String matName;

    @Schema(description = "物料编号")
    private String matNo;

    @Schema(description = "物料规格")
    private String pro;

    @Schema(description = "来源属性")
    private String sourceProperty;

    @Schema(description = "客商名称")
    private String custName;

    @Schema(description = "物料颜色")
    private String matColor;

    @Schema(description = "成本金额")
    private BigDecimal costAmount;

    @Schema(description = "报价金额")
    private BigDecimal quoteAmount;

    @Schema(description = "前端用的")
    private String tempId;

    @Schema(description = "产品编码")
    private String productCode;

    private List<BaseBomProductVO> children;
}
