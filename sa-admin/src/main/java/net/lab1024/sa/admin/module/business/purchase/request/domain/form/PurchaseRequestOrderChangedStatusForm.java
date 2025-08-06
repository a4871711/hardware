package net.lab1024.sa.admin.module.business.purchase.request.domain.form;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description TODO
 * @Author 赵嘉伟
 * @Date 23:19 2025/6/29
 * @Version 1.0
 **/
@Data
public class PurchaseRequestOrderChangedStatusForm implements Serializable {

    private List<Long> purchaseRequestOrderDetailIdList;

    private String status;
}
