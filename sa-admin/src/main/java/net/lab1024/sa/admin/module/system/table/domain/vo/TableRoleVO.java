package net.lab1024.sa.admin.module.system.table.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.system.employee.domain.vo.EmployeeVO;
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleVO;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 表格的列所隐藏的角色 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-04-04 23:12:00
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class TableRoleVO extends CompanyBaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long tableRoleId;

    @Schema(description = "前端的table_id字段")
    private Integer tableId;

    @Schema(description = "要隐藏的具体的表格的列")
    private String columns;

    @Schema(description = "要隐藏的角色列表")
    private List<RoleVO> roleList;

    @Schema(description = "要隐藏的角色列表")
    private String roleId;

    @Schema(description = "要隐藏的用户列表")
    private String employeeId;

    @Schema(description = "要隐藏的用户列表")
    private List<EmployeeVO> employeeList;
}
