package net.lab1024.sa.admin.module.system.company.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 公司资料 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-03-05 18:27:44
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_company")
public class CompanyBaseEntity extends net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity {

    /**
     * 公司id(租户号)
     */
    @TableId
    private Long companyId;

    /**
     * 公司代码
     */
    private String companyCode;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司logo
     */
    private Long companyLogoId;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String contactPhone;

    /**
     * 联系人邮箱
     */
    private String contactEmail;

    /**
     * 公司地址
     */
    private String companyAddress;

    /**
     * 订阅计划
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
     * 统一社会信用代码
     */
    private String unifiedSocialCreditCode;

    /**
     * 营业执照
     */
    private Long businessLicenseId;

    /**
     * 账户状态
     */
    private Integer accountStatus;

    /**
     * 公司网站
     */
    private String website;

    /**
     * 身份证正面
     */
    private Long idCardFrontId;

    /**
     * 身份证背面
     */
    private Long idCardBackId;

}
