package net.lab1024.sa.admin.module.business.base.mat.mattype.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

/**
 * 物料分类表 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-04-01 22:25:10
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseMatTypeVO extends CompanyBaseEntity {


    @Schema(description = "物料分类id")
    private Long matTypeId;

    @Schema(description = "分类编号")
    private String matTypeCode;

    @Schema(description = "分类名称")
    private String matTypeName;

    @Schema(description = "上级分类id")
    private Long matTypeParentId;

    @Schema(description = "备注")
    private String remark;

}
