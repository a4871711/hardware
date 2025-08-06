package net.lab1024.sa.admin.module.business.base.bom.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description TODO
 * @Author 赵嘉伟
 * @Date 15:30 2025/5/3
 * @Version 1.0
 **/
@Data
public class BaseBomProductSummaryVO {

    private BigDecimal costAmountCount;

    private BigDecimal quoteAmountCount;
}
