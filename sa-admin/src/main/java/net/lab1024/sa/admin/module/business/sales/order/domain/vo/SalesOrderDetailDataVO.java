package net.lab1024.sa.admin.module.business.sales.order.domain.vo;

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
public class SalesOrderDetailDataVO implements Serializable {

    private SalesOrderVO salesOrder;

    private List<SalesOrderDetailVO> salesOrderDetailList;

    private List<SalesOrderRequirementsVO> salesOrderRequirementsList;

    private List<SalesOrderMatVO> salesOrderMatList;
}
