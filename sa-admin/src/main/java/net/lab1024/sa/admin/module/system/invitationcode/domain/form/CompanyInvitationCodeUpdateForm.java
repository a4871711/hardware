package net.lab1024.sa.admin.module.system.invitationcode.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * 公司邀请码表 更新表单
 *
 * @Author 赵嘉伟
 * @Date 2025-03-10 19:42:19
 * @Copyright 赵嘉伟
 */

@Data
public class CompanyInvitationCodeUpdateForm {

    @Schema(description = "公司邀请码id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "公司邀请码id 不能为空")
    private Long invitationCodeId;

    @Schema(description = "订阅计划", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订阅计划不能为空")
    private Integer subscriptionPlan;

    @Schema(description = "订阅开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订阅开始时间不能为空")
    private LocalDate subscriptionStartDate;

    @Schema(description = "订阅结束时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订阅结束时间不能为空")
    private LocalDate subscriptionEndDate;

    @Schema(description = "邀请码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "邀请码不能为空")
    private String invitationCode;

    @Schema(description = "公司id")
    private Long companyId;

    @Schema(description = "版本号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "版本号不能为空")
    private Integer version;
}