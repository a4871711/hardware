package net.lab1024.sa.admin.module.business.base.mat.matcust.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import net.lab1024.sa.admin.module.business.base.mat.matcust.domain.entity.BaseMatCustEntity;

/**
 * 物料供应商单价表 更新表单
 *
 * @Author 赵嘉伟
 * @Date 2025-04-22 00:40:08
 * @Copyright 赵嘉伟
 */

@Data
public class BaseMatCustUpdateForm extends BaseMatCustEntity {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "主键 不能为空")
    private Long matCustId;

}