package net.lab1024.sa.admin.module.business.sales.order.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.sales.order.domain.entity.SalesOrderDetailEntity;

/**
 * 销售订货单明细表 新建表单
 *
 * @Author 赵嘉伟
 * @Date 2025-05-25 19:36:41
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class SalesOrderDetailAddForm extends SalesOrderDetailEntity {


    @Schema(description = "销售订货单id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "销售订货单id 不能为空")
    private Long salesOrderId;

    @Schema(description = "多级bom的id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "多级bom的id 不能为空")
    private Long bomId;


}