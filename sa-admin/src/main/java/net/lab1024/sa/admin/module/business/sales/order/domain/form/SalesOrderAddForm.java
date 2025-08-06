package net.lab1024.sa.admin.module.business.sales.order.domain.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.sales.order.domain.entity.SalesOrderEntity;

import java.util.List;

/**
 * 销售订货单 新建表单
 *
 * @Author 赵嘉伟
 * @Date 2025-05-25 19:36:32
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class SalesOrderAddForm extends SalesOrderEntity {

    private List<SalesOrderDetailAddForm> salesOrderDetailList;

    private List<SalesOrderRequirementsAddForm> salesOrderRequirementsList;

    private List<SalesOrderMatAddForm> salesOrderMatAddForms;

}