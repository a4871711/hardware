package net.lab1024.sa.admin.module.business.purchase.request.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description TODO
 * @Author 赵嘉伟
 * @Date 09:02 2025/5/31
 * @Version 1.0
 **/
@Data
public class PurchaseRequestOrderDetailDataVO implements Serializable {

    private PurchaseRequestOrderVO purchaseRequestOrder;

    private List<PurchaseRequestOrderDetailVO> purchaseRequestOrderDetailList;
}
