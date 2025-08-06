package net.lab1024.sa.admin.module.business.base.bom.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.math.BigDecimal;

/**
 * 多级BOM-主表 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-04-25 23:33:04
 * @Copyright 赵嘉伟
 */

@Data
@TableName("t_base_bom")
public class BaseBomEntity extends CompanyBaseEntity {

    /**
     * 主键
     */
    @TableId
    private Long bomId;

    /**
     * 产品编号
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 客户id
     */
    private Long custId;

    /**
     * 系列
     */
    private String seriesName;

    /**
     * 客户产品编号
     */
    private String custProductCode;

    /**
     * 客户产品名称
     */
    private String custProductName;

    /**
     * 英文颜色
     */
    private String productEngColor;

    /**
     * 产品分类
     */
    private String productClass;

    /**
     * 产品颜色
     */
    private String productColor;

    /**
     * 规格
     */
    private String pro;

    /**
     * 备注
     */
    private String remark;

    /**
     * 材料金额
     */
    private BigDecimal matAmount;

    /**
     * 费用金额
     */
    private BigDecimal expenseAmount;

    /**
     * 成品总金额
     */
    private BigDecimal totalCostAmount;

    /**
     * 报价总金额
     */
    private BigDecimal totalQuoteAmount;

    /**
     * 损耗率
     */
    private BigDecimal waste;

    /**
     * 含税类型
     */
    private String taxType;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 销售单价
     */
    private BigDecimal salePrice;

    /**
     * 图片
     */
    private String productImage;

    /**
     * 是否作废
     */
    private Boolean invalid;

    /**
     * 是否审核
     */
    private Boolean audit;

}
