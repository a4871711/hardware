package net.lab1024.sa.admin.module.business.base.bom.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.math.BigDecimal;

/**
 * 多级bom其他费用 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-04-30 21:05:57
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseBomOtherAmountVO extends CompanyBaseEntity {


    @Schema(description = "主键")
    private Long bomOtherAmountId;

    @Schema(description = "bom_id")
    private Long bomId;

    @Schema(description = "名称")
    private String otherAmountName;

    @Schema(description = "金额")
    private BigDecimal amount;

    @Schema(description = "损耗")
    private BigDecimal waste;

    private String tempId;

}
