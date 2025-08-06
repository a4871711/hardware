package net.lab1024.sa.admin.module.business.base.custbank.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 银行客商功能表 新建表单
 *
 * @Author 赵嘉伟
 * @Date 2025-03-28 23:41:32
 * @Copyright 赵嘉伟
 */

@Data
public class BaseCustBankAddForm {


    @Schema(description = "客商主键id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "客商主键id 不能为空")
    private Long custId;

    @Schema(description = "银行id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "银行id 不能为空")
    private Long bankId;

    @Schema(description = "公司id(租户号)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "公司id(租户号) 不能为空")
    private Long companyId;

}