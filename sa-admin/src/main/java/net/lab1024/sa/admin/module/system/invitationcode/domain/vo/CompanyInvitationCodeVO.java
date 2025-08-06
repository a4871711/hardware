package net.lab1024.sa.admin.module.system.invitationcode.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.time.LocalDateTime;

/**
 * 公司邀请码表 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-03-10 19:42:19
 * @Copyright 赵嘉伟
 */

@Data
public class CompanyInvitationCodeVO extends CompanyBaseEntity {


    @Schema(description = "公司邀请码id")
    private Long invitationCodeId;

    @Schema(description = "订阅计划 (0:基础版, 1:高级版, 2:企业版, 999: 管理员版)")
    private Integer subscriptionPlan;

    @Schema(description = "订阅开始时间")
    private LocalDateTime subscriptionStartDate;

    @Schema(description = "订阅到期时间")
    private LocalDateTime subscriptionEndDate;

    @Schema(description = "邀请码")
    private String invitationCode;

    @Schema(description = "公司名称")
    private String companyName;

}
