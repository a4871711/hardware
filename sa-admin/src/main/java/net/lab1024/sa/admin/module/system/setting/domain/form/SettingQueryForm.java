package net.lab1024.sa.admin.module.system.setting.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.domain.PageParam;

/**
 * 系统参数设置-主表 分页查询表单
 *
 * @Author 赵嘉伟
 * @Date 2025-04-20 17:30:24
 * @Copyright 赵嘉伟
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class SettingQueryForm extends PageParam {

    @Schema(description = "参数id")
    private String paramId;

    @Schema(description = "主菜单名称")
    private String firstMenuName;

    @Schema(description = "子菜单名称")
    private String secondMenuName;

}
