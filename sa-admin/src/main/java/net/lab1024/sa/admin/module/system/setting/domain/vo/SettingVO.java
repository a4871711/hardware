package net.lab1024.sa.admin.module.system.setting.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.LogBaseEntity;

/**
 * 系统参数设置-主表 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-04-20 17:30:24
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class SettingVO extends LogBaseEntity {


    @Schema(description = "系统参数设置id")
    private Long settingId;

    @Schema(description = "参数id")
    private String paramId;

    @Schema(description = "主菜单名称")
    private String firstMenuName;

    @Schema(description = "子菜单名称")
    private String secondMenuName;

    @Schema(description = "参数说明")
    private String paramDescription;

    @Schema(description = "参数值")
    private String paramValue;

    @Schema(description = "公司参数id")
    private Long companySettingId;

}
