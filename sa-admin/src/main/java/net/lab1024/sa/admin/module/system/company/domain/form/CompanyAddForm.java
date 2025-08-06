package net.lab1024.sa.admin.module.system.company.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.system.company.domain.entity.CompanyBaseEntity;
import net.lab1024.sa.base.common.domain.FileBO;

import java.util.List;

/**
 * 公司资料 新建表单
 *
 * @Author 赵嘉伟
 * @Date 2025-03-05 18:27:44
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class CompanyAddForm extends CompanyBaseEntity {

    @Schema(description = "公司代码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "公司代码不能为空")
    private String companyCode;

    @Schema(description = "公司名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "公司名称不能为空")
    private String companyName;

    @Schema(description = "联系人姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "联系人姓名不能为空")
    private String contactName;

    @Schema(description = "联系人电话", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "联系人电话不能为空")
    private String contactPhone;

    @Schema(description = "邀请码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "邀请码不能为空")
    private String invitationCode;

    private List<FileBO> logoFileList;

    private List<FileBO> licenseFileList;

    private List<FileBO> idCardFrontFileList;

    private List<FileBO> idCardBackFileList;
}