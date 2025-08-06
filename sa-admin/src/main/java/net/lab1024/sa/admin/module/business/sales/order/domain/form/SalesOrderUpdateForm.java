package net.lab1024.sa.admin.module.business.sales.order.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.sales.order.domain.entity.SalesOrderEntity;

/**
 * 销售订货单 更新表单
 *
 * @Author 赵嘉伟
 * @Date 2025-05-25 19:36:32
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class SalesOrderUpdateForm extends SalesOrderEntity {

    @Schema(description = "销售订货单id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "销售订货单id 不能为空")
    private Long salesOrderId;

    @Schema(description = "版本号(乐观锁)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "版本号(乐观锁) 不能为空")
    private Integer version;

}