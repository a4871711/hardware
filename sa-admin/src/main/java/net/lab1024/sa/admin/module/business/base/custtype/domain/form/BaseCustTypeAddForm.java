package net.lab1024.sa.admin.module.business.base.custtype.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.base.custtype.domain.entity.BaseCustTypeEntity;

/**
 * 客商类型 新建表单
 *
 * @Author 赵嘉伟
 * @Date 2025-03-28 21:27:26
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseCustTypeAddForm extends BaseCustTypeEntity {

    @NotNull(message = "客商分类名称不能为空")
    @Schema(description = "客商分类名称")
    private String custTypeName;

    @NotNull(message = "客商分类编码不能为空")
    @Schema(description = "客商分类编码")
    private String custTypeCode;
}