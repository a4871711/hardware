package net.lab1024.sa.admin.module.business.purchase.order.domain.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.purchase.order.domain.entity.PurchaseOrderEntity;

import java.util.List;

/**
 * 采购订货单 新建表单
 *
 * @Author 赵嘉伟
 * @Date 2025-07-08 22:10:23
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class PurchaseOrderAddForm extends PurchaseOrderEntity {

    private List<PurchaseOrderDetailAddForm> purchaseOrderDetailAddFormList;

}