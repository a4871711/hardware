package net.lab1024.sa.admin.module.business.sales.order.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.sales.order.domain.entity.SalesOrderEntity;
import net.lab1024.sa.base.common.json.serializer.FileKeyVoSerializer;

import java.math.BigDecimal;

/**
 * @Description TODO
 * @Author 赵嘉伟
 * @Date 12:54 2025/5/31
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SalesOrderQuerySearchVO extends SalesOrderEntity {

    // 客户名称
    private String custName;

    // 产品编码
    private String productCode;

    // 产品名称
    private String productName;

    // 客户产品编码
    private String custProductCode;

    // 客户产品名称
    private String custProductName;

    // 系列名称
    private String seriesName;

    // 产品颜色
    private String productColor;

    // 产品英文颜色
    private String productEngColor;

    // 产品分类
    private String productClass;

    // 订单数量
    private BigDecimal orderQty;

    // 投产数量
    private BigDecimal productionQty;

    // 单价（不含税）
    private BigDecimal unitPrice;

    // 含税单价
    private BigDecimal taxUnitPrice;

    // 金额（不含税）
    private BigDecimal amount;

    // 税额
    private BigDecimal taxAmount;

    // 金额（含税）
    private BigDecimal taxTotalAmount;

    // 备注
    private String remark;

    private String productionType;

    @Schema(description = "产品图片")
    @JsonSerialize(using = FileKeyVoSerializer.class)
    private String productImage;


}
