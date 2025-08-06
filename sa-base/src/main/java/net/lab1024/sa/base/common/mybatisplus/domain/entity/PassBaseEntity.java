package net.lab1024.sa.base.common.mybatisplus.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;


@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class PassBaseEntity extends LogBaseEntity implements Serializable {

    /** 审核时间 */
    @TableField(value = "pass_time")
    private LocalDateTime passTime;

    /** 审核员工姓名 */
    @TableField(value = "pass_user_name")
    private String passUserName;

    /** 审核员工ID */
    @TableField(value = "pass_user_id")
    private Long passUserId;



}
