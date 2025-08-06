package net.lab1024.sa.admin.module.system.subscriptionplanmenu.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.LogBaseEntity;

/**
 * 订阅计划菜单权限 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-03-11 08:17:27
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class SubscriptionPlanMenuVO extends LogBaseEntity {


    @Schema(description = "菜单ID")
    private Long menuId;

    @Schema(description = "菜单名称")
    private String menuName;

    @Schema(description = "类型")
    private Integer menuType;

    @Schema(description = "父菜单ID")
    private Long parentId;

    @Schema(description = "显示顺序")
    private Integer sort;

    @Schema(description = "路由地址")
    private String path;

    @Schema(description = "组件路径")
    private String component;

    @Schema(description = "权限类型")
    private Integer permsType;

    @Schema(description = "后端权限字符串")
    private String apiPerms;

    @Schema(description = "前端权限字符串")
    private String webPerms;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "功能点关联菜单ID")
    private Long contextMenuId;

    @Schema(description = "是否为外链")
    private Boolean frameFlag;

    @Schema(description = "外链地址")
    private String frameUrl;

    @Schema(description = "是否缓存")
    private Boolean cacheFlag;

    @Schema(description = "显示状态")
    private Boolean visibleFlag;

    @Schema(description = "禁用状态")
    private Boolean disabledFlag;

    @Schema(description = "订阅计划 (0:基础版, 1:高级版, 2:企业版, 999: 管理员版)")
    private Integer subscriptionPlan;

}
