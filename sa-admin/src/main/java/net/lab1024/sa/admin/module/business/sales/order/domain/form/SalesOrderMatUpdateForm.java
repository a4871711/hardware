package net.lab1024.sa.admin.module.business.sales.order.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import net.lab1024.sa.admin.module.business.sales.order.domain.entity.SalesOrderMatEntity;

/**
 * 销售订单原材料表 更新表单
 *
 * @Author 赵嘉伟
 * @Date 2025-05-29 23:32:37
 * @Copyright 赵嘉伟
 */

@Data
public class SalesOrderMatUpdateForm extends SalesOrderMatEntity {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "主键 不能为空")
    private Long salesOrderMatId;

}