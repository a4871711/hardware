package net.lab1024.sa.admin.module.business.base.bom.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description TODO
 * @Author 赵嘉伟
 * @Date 10:26 2025/5/3
 * @Version 1.0
 **/
@Data
public class BaseBomDetailVO implements Serializable {

    private BaseBomVO baseBom;

    private BaseBomProductSummaryVO bomProductSummary;

    private List<BaseBomProductVO> bomProductList;

    private List<BaseBomOtherAmountVO> bomOtherAmountList;
}
