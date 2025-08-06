package net.lab1024.sa.admin.module.business.purchase.request.domain.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.purchase.request.domain.entity.PurchaseRequestOrderEntity;

import java.util.List;

/**
 * 采购申请单 新建表单
 *
 * @Author 赵嘉伟
 * @Date 2025-06-23 22:34:10
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class PurchaseRequestOrderAddForm extends PurchaseRequestOrderEntity {

    /**
     * 采购申请单详情
     */
    private List<PurchaseRequestOrderDetailAddForm> purchaseRequestOrderDetailAddFormList;

}