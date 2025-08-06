package net.lab1024.sa.admin.module.business.purchase.order.domain.form;


import lombok.Data;

/**
 * 采购订货单 新建表单
 *
 * @Author 赵嘉伟
 * @Date 2025-07-08 22:10:23
 * @Copyright 赵嘉伟
 */

@Data
public class PurchaseOrderAuditForm {

    private Long purchaseOrderId;

    private Boolean audit;

}