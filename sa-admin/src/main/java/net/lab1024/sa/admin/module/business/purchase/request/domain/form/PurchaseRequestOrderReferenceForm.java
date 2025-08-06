package net.lab1024.sa.admin.module.business.purchase.request.domain.form;

import lombok.Data;

import java.util.List;

/**
 * @Description TODO
 * @Author 赵嘉伟
 * @Date 17:51 2025/7/5
 * @Version 1.0
 **/
@Data
public class PurchaseRequestOrderReferenceForm {

    private List<Long> salesOrderIds;
}
