package net.lab1024.sa.admin.module.system.table.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

/**
 * 表格的列所隐藏的角色 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-04-04 23:12:00
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_table_role")
public class TableRoleEntity extends CompanyBaseEntity {

    /**
     * 主键
     */
    @TableId
    private Long tableRoleId;

    /**
     * 前端的table_id字段
     */
    private Integer tableId;

    /**
     * 要隐藏的角色列表
     */
    private String roleId;

    /**
     * 要隐藏的具体的用户的id
     */
    private String employeeId;

    /**
     * 要隐藏的具体的表格的列
     */
    private String columns;

}
