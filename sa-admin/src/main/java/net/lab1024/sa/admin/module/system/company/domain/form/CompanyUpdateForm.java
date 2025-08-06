package net.lab1024.sa.admin.module.system.company.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.system.company.domain.entity.CompanyBaseEntity;

/**
 * 公司资料 更新表单
 *
 * @Author 赵嘉伟
 * @Date 2025-03-05 18:27:44
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class CompanyUpdateForm extends CompanyBaseEntity {

    @Schema(description = "公司id(租户号)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "公司id(租户号) 不能为空")
    private Long companyId;

}