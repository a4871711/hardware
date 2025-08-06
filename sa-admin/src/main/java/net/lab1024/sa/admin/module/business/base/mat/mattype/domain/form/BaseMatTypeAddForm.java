package net.lab1024.sa.admin.module.business.base.mat.mattype.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.base.mat.mat.domain.entity.BaseMatEntity;

/**
 * 物料分类表 新建表单
 *
 * @Author 赵嘉伟
 * @Date 2025-04-01 22:25:10
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseMatTypeAddForm extends BaseMatEntity {

    /**
     * 分类编号
     */
    @Schema(description = "分类编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "物料分类编码不能为空")
    private String matTypeCode;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "分类名称不能为空")
    private String matTypeName;

    /**
     * 上级分类id
     */
    @Schema(description = "上级分类id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long matTypeParentId;

    /**
     * 备注
     */
    @Schema(description = "备注", requiredMode = Schema.RequiredMode.REQUIRED)
    private String remark;
}