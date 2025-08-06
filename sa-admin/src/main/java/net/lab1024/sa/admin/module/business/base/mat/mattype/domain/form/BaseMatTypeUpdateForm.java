package net.lab1024.sa.admin.module.business.base.mat.mattype.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.base.mat.mattype.domain.entity.BaseMatTypeEntity;

/**
 * 物料分类表 更新表单
 *
 * @Author 赵嘉伟
 * @Date 2025-04-01 22:25:10
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseMatTypeUpdateForm extends BaseMatTypeEntity {

    @Schema(description = "物料分类id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "物料分类id 不能为空")
    private Long matTypeId;


    @Schema(description = "版本号(乐观锁)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "版本号(乐观锁) 不能为空")
    private Integer version;

}