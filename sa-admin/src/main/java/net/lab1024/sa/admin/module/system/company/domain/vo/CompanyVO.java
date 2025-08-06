package net.lab1024.sa.admin.module.system.company.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.domain.FileBO;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.time.LocalDate;
import java.util.List;

/**
 * 公司资料 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-03-05 18:27:44
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class CompanyVO extends CompanyBaseEntity {


    @Schema(description = "公司id(租户号)")
    private Long companyId;

    @Schema(description = "公司代码")
    private String companyCode;

    @Schema(description = "公司名称")
    private String companyName;

    @Schema(description = "公司logo")
    private Long companyLogoId;

    @Schema(description = "联系人姓名")
    private String contactName;

    @Schema(description = "联系人电话")
    private String contactPhone;

    @Schema(description = "联系人邮箱")
    private String contactEmail;

    @Schema(description = "公司地址")
    private String companyAddress;

    @Schema(description = "订阅计划")
    private Integer subscriptionPlan;

    @Schema(description = "订阅开始时间")
    private LocalDate subscriptionStartDate;

    @Schema(description = "订阅到期时间")
    private LocalDate subscriptionEndDate;

    @Schema(description = "统一社会信用代码")
    private String unifiedSocialCreditCode;

    @Schema(description = "营业执照")
    private Long businessLicenseId;

    @Schema(description = "账户状态")
    private Integer accountStatus;

    @Schema(description = "公司网站")
    private String website;

    @Schema(description = "身份证正面")
    private Long idCardFrontId;

    @Schema(description = "身份证背面")
    private Long idCardBackId;

    private List<FileBO> logoFileList;

    private List<FileBO> licenseFileList;

    private List<FileBO> idCardFrontFileList;

    private List<FileBO> idCardBackFileList;

}
