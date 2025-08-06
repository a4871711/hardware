package net.lab1024.sa.base.common.mybatisplus.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @className: CompanyBaseEntity
 * @author: D7608
 * @date: 2025/3/18 10:39
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LogBaseEntity extends BaseEntity {
    /**
     * 创建时间
     */
    @TableField(value = "create_time", updateStrategy = FieldStrategy.NEVER, fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 创建员工ID
     */
    @TableField(value = "create_user_id", updateStrategy = FieldStrategy.NEVER, fill = FieldFill.INSERT)
    private Long createUserId;

    /**
     * 创建员工姓名
     */
    @TableField(value = "create_user_name", updateStrategy = FieldStrategy.NEVER, fill = FieldFill.INSERT)
    private String createUserName;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 修改员工id
     */
    @TableField(value = "update_user_id", fill = FieldFill.UPDATE)
    private Long updateUserId;

    /**
     * 修改员工姓名
     */
    @TableField(value = "update_user_name", fill = FieldFill.UPDATE)
    private String updateUserName;


}
