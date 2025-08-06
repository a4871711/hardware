
package net.lab1024.sa.admin.module.system.invitationcode.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * 公司邀请码表 新建表单
 *
 * @Author 赵嘉伟
 * @Date 2025-03-10 19:42:19
 * @Copyright 赵嘉伟
 */

@Data
public class CompanyInvitationCodeAddForm {

    @Schema(description = "邀请码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "邀请码 不能为空")
    private String invitationCode;

    @Schema(description = "订阅计划 (0:基础版, 1:高级版, 2:企业版, 999: 管理员版)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订阅计划 不能为空")
    private Integer subscriptionPlan;

    @Schema(description = "订阅开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订阅开始时间 不能为空")
    private LocalDate subscriptionStartDate;

    @Schema(description = "订阅到期时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订阅到期时间 不能为空")
    private LocalDate subscriptionEndDate;
}
