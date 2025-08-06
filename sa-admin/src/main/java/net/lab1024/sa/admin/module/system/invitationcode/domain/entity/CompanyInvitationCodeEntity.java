package net.lab1024.sa.admin.module.system.invitationcode.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.LogBaseEntity;

import java.time.LocalDate;

/**
 * 公司邀请码表 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-03-10 19:42:19
 * @Copyright 赵嘉伟
 */

@Data
@TableName("t_company_invitation_code")
public class CompanyInvitationCodeEntity extends LogBaseEntity {

    /**
     * 公司邀请码id
     */
    @TableId
    private Long invitationCodeId;

    /**
     * 订阅计划 (0:基础版, 1:高级版, 2:企业版)
     */
    private Integer subscriptionPlan;

    /**
     * 订阅开始时间
     */
    private LocalDate subscriptionStartDate;

    /**
     * 订阅到期时间
     */
    private LocalDate subscriptionEndDate;

    /**
     * 邀请码
     */
    private String invitationCode;

    private Long companyId;
}
