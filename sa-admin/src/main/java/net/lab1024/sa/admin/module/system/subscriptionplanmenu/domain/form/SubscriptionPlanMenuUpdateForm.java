package net.lab1024.sa.admin.module.system.subscriptionplanmenu.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 订阅计划菜单权限 更新表单
 *
 * @Author 赵嘉伟
 * @Date 2025-03-11 08:17:27
 * @Copyright 赵嘉伟
 */

@Data
public class SubscriptionPlanMenuUpdateForm extends SubscriptionPlanMenuBaseForm {

    @Schema(description = "菜单ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "菜单ID 不能为空")
    private Long menuId;

    @Schema(hidden = true)
    private Long updateUserId;

}