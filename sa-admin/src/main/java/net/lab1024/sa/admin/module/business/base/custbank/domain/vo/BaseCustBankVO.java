package net.lab1024.sa.admin.module.business.base.custbank.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 银行客商功能表 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-03-28 23:41:32
 * @Copyright 赵嘉伟
 */

@Data
public class BaseCustBankVO {


    @Schema(description = "主键")
    private Long custBankId;

    @Schema(description = "客商主键id")
    private Long custId;

    @Schema(description = "银行id")
    private Long bankId;

    @Schema(description = "公司id(租户号)")
    private Long companyId;

    @Schema(description = "创建人")
    private Long createUserId;

    @Schema(description = "创建人姓名")
    private String createUserName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
