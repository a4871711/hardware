package net.lab1024.sa.admin.module.system.invitationcode.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.domain.PageParam;

/**
 * 公司邀请码表 分页查询表单
 *
 * @Author 赵嘉伟
 * @Date 2025-03-10 19:42:19
 * @Copyright 赵嘉伟
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class CompanyInvitationCodeQueryForm extends PageParam {

    @Schema(description = "邀请码")
    private String invitationCode;

    @Schema(description = "订阅方案")
    private Integer subscriptionPlan;
}
