package net.lab1024.sa.admin.module.system.setting.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.LogBaseEntity;

/**
 * 系统参数设置-主表 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-04-20 17:30:24
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_setting")
public class SettingEntity extends LogBaseEntity {

    /**
     * 系统参数设置id
     */
    @TableId
    private Long settingId;

    /**
     * 参数id
     */
    private String paramId;

    /**
     * 主菜单名称
     */
    private String firstMenuName;

    /**
     * 子菜单名称
     */
    private String secondMenuName;

    /**
     * 参数说明
     */
    private String paramDescription;

    /**
     * 参数值
     */
    private String paramValue;
}
