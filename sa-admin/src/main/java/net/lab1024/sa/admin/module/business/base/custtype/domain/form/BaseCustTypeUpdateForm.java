package net.lab1024.sa.admin.module.business.base.custtype.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.base.custtype.domain.entity.BaseCustTypeEntity;

/**
 * 客商类型 更新表单
 *
 * @Author 赵嘉伟
 * @Date 2025-03-28 21:27:26
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseCustTypeUpdateForm extends BaseCustTypeEntity {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "主键 不能为空")
    private Long custTypeId;


    @Schema(description = "版本号(乐观锁)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "版本号(乐观锁) 不能为空")
    private Integer version;


}