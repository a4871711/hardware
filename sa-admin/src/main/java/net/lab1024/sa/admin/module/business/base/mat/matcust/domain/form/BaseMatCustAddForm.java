package net.lab1024.sa.admin.module.business.base.mat.matcust.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import net.lab1024.sa.admin.module.business.base.mat.matcust.domain.entity.BaseMatCustEntity;

/**
 * 物料供应商单价表 新建表单
 *
 * @Author 赵嘉伟
 * @Date 2025-04-22 00:40:08
 * @Copyright 赵嘉伟
 */

@Data
public class BaseMatCustAddForm extends BaseMatCustEntity {


    @Schema(description = "物料id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "物料id 不能为空")
    private Long matId;

    @Schema(description = "供应商id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "供应商id 不能为空")
    private Long custId;

}