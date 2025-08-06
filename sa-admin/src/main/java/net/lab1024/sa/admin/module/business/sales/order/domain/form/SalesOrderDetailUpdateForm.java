package net.lab1024.sa.admin.module.business.sales.order.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 销售订货单明细表 更新表单
 *
 * @Author 赵嘉伟
 * @Date 2025-05-25 19:36:41
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class SalesOrderDetailUpdateForm extends SalesOrderDetailAddForm {

    @Schema(description = "销售订货单明细id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "销售订货单明细id 不能为空")
    private Long salesOrderDetailId;

    @Schema(description = "版本号(乐观锁)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "版本号(乐观锁) 不能为空")
    private Integer version;

}