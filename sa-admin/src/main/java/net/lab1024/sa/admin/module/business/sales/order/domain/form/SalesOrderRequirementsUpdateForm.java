package net.lab1024.sa.admin.module.business.sales.order.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.sales.order.domain.entity.SalesOrderRequirementsEntity;

/**
 * 销售订单-订单要求表 更新表单
 *
 * @Author 赵嘉伟
 * @Date 2025-05-25 19:36:51
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class SalesOrderRequirementsUpdateForm extends SalesOrderRequirementsEntity {

    @Schema(description = "销售订单-订单要求表id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "销售订单-订单要求表id 不能为空")
    private Long salesOrderRequirementsId;

    @Schema(description = "版本号(乐观锁)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "版本号(乐观锁) 不能为空")
    private Integer version;

}