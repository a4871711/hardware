package net.lab1024.sa.admin.module.business.base.mat.mat.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.json.serializer.DictValueVoSerializer;
import net.lab1024.sa.base.common.json.serializer.FileKeyVoSerializer;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.math.BigDecimal;

/**
 * 物料资料表 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-04-01 22:25:04
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseMatVO extends CompanyBaseEntity {


    @Schema(description = "物料id")
    private Long matId;

    @Schema(description = "物料分类id")
    private Long matTypeId;

    @Schema(description = "物料编号")
    private String matNo;

    @Schema(description = "物料名称")
    private String matName;

    @Schema(description = "规格")
    private String pro;

    @Schema(description = "颜色")
    private String colorName;

    @Schema(description = "颜色id")
    private Long colorId;

    @Schema(description = "计算单位")
    private String calculationUnit;

    @Schema(description = "采购单位")
    private String purchaseUnit;

    @Schema(description = "单位转换系数")
    private BigDecimal conversionFactor;

    @Schema(description = "来源属性")
    @JsonSerialize(using = DictValueVoSerializer.class)
    private String sourceProperty;

    @Schema(description = "图片")
    @JsonSerialize(using = FileKeyVoSerializer.class)
    private String matImage;

    @Schema(description = "重量单位")
    private String weightUnit;

    @Schema(description = "重量系数")
    private String weightFactor;

    @Schema(description = "备注")
    private String remark;

    private Boolean invalid;

}
