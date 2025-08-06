package net.lab1024.sa.admin.module.business.purchase.request.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.LogBaseEntity;

/**
 * 采购申请单 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-06-23 22:34:10
 * @Copyright 赵嘉伟
 */

@Data
@TableName("t_purchase_request_order")
public class PurchaseRequestOrderEntity extends LogBaseEntity {

    /**
     * 采购订货单id
     */
    @TableId
    private Long purchaseRequestOrderId;

    private String purchaseRequestOrderCode;

    private Boolean invalid;

}
