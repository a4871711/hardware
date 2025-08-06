package net.lab1024.sa.admin.module.system.subscriptionplanmenu.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.LogBaseEntity;

/**
 * 订阅计划菜单权限 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-03-11 08:17:27
 * @Copyright 赵嘉伟
 */

@Data
@TableName("t_subscription_plan_menu")
public class SubscriptionPlanMenuBaseEntity extends LogBaseEntity {

    /**
     * 菜单ID
     */
    @TableId(type = IdType.AUTO)
    private Long menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 类型
     */
    private Integer menuType;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 权限类型
     */
    private Integer permsType;

    /**
     * 后端权限字符串
     */
    private String apiPerms;

    /**
     * 前端权限字符串
     */
    private String webPerms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 功能点关联菜单ID
     */
    private Long contextMenuId;

    /**
     * 是否为外链
     */
    private Boolean frameFlag;

    /**
     * 外链地址
     */
    private String frameUrl;

    /**
     * 是否缓存
     */
    private Boolean cacheFlag;

    /**
     * 显示状态
     */
    private Boolean visibleFlag;

    /**
     * 禁用状态
     */
    private Boolean disabledFlag;

    /**
     * 订阅计划 (0:基础版, 1:高级版, 2:企业版)
     */
    private Integer subscriptionPlan;

}
