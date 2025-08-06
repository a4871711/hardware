package net.lab1024.sa.admin.module.business.sales.order.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

/**
 * 销售订单-订单要求表 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-05-25 19:36:51
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_sales_order_requirements")
public class SalesOrderRequirementsEntity extends CompanyBaseEntity {

    /**
     * 销售订单-订单要求表id
     */
    @TableId
    private Long salesOrderRequirementsId;

    private Long bomId;

    /**
     * 销售订货单id
     */
    private Long salesOrderId;

    /**
     * 要求类别
     */
    private String requirementType;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片1
     */
    private String image1;

    /**
     * 图片2
     */
    private String image2;

    /**
     * 图片3
     */
    private String image3;

    /**
     * 图片4
     */
    private String image4;

    /**
     * 备注
     */
    private String remark;

}
