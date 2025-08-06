package net.lab1024.sa.admin.module.system.table.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

/**
 * 表格的列所隐藏的角色 新建表单
 *
 * @Author 赵嘉伟
 * @Date 2025-04-04 23:12:00
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class TableRoleAddForm extends CompanyBaseEntity {


    @Schema(description = "前端的table_id字段", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "前端的table_id字段 不能为空")
    private Integer tableId;

    @Schema(description = "要隐藏的角色列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private String roleId;

    @Schema(description = "要隐藏的具体的用户的id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String employeeId;

    @Schema(description = "要隐藏的具体的表格的类", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "要隐藏的具体的表格的类 不能为空")
    private String columns;
}