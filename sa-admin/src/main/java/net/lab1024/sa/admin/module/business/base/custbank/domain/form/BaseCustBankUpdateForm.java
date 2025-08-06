package net.lab1024.sa.admin.module.business.base.custbank.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 银行客商功能表 更新表单
 *
 * @Author 赵嘉伟
 * @Date 2025-03-28 23:41:32
 * @Copyright 赵嘉伟
 */

@Data
public class BaseCustBankUpdateForm {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "主键 不能为空")
    private Long custBankId;

}