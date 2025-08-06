package net.lab1024.sa.admin.module.business.base.bom.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.json.serializer.FileKeyVoSerializer;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.math.BigDecimal;

/**
 * 多级BOM-主表 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-04-25 23:33:04
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseBomVO extends CompanyBaseEntity {

    @Schema(description = "主键")
    private Long bomId;

    @Schema(description = "产品编号")
    private String productCode;

    @Schema(description = "产品名称")
    private String productName;

    @Schema(description = "客户姓名")
    private String custName;

    @Schema(description = "客户id")
    private Long custId;

    @Schema(description = "系列")
    private String seriesName;

    @Schema(description = "客户产品编号")
    private String custProductCode;

    @Schema(description = "客户产品名称")
    private String custProductName;

    @Schema(description = "英文颜色")
    private String productEngColor;

    @Schema(description = "产品分类")
    private String productClass;

    @Schema(description = "产品颜色")
    private String productColor;

    @Schema(description = "规格")
    private String pro;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "材料金额")
    private BigDecimal matAmount;

    @Schema(description = "费用金额")
    private BigDecimal expenseAmount;

    @Schema(description = "成品总金额")
    private BigDecimal totalCostAmount;

    @Schema(description = "报价总金额")
    private BigDecimal totalQuoteAmount;

    @Schema(description = "损耗率")
    private BigDecimal waste;

    @Schema(description = "含税类型")
    private String taxType;

    @Schema(description = "税率")
    private BigDecimal taxRate;

    @Schema(description = "销售单价")
    private BigDecimal salePrice;

    @JsonSerialize(using = FileKeyVoSerializer.class)
    @Schema(description = "图片")
    private String productImage;

    @Schema(description = "是否作废")
    private Boolean invalid;

    @Schema(description = "是否审核")
    private Boolean audit;

    @Schema(description = "材料编号")
    private String matNo;

    @Schema(description = "材料名称")
    private String matName;

    @Schema(description = "来源属性")
    private String sourceProperty;

    @Schema(description = "材料颜色")
    private String matColorName;

    @Schema(description = "用量")
    private BigDecimal usageAmount;

    @Schema(description = "单价")
    private BigDecimal unitPrice;

}
