package net.lab1024.sa.admin.module.business.sales.order.domain.form;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.sales.order.domain.entity.SalesOrderRequirementsEntity;
import net.lab1024.sa.base.common.json.deserializer.FileKeyVoDeserializer;

/**
 * 销售订单-订单要求表 新建表单
 *
 * @Author 赵嘉伟
 * @Date 2025-05-25 19:36:51
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class SalesOrderRequirementsAddForm extends SalesOrderRequirementsEntity {

    /**
     * 图片1
     */
    @JsonDeserialize(using = FileKeyVoDeserializer.class)
    private String image1;

    /**
     * 图片2
     */
    @JsonDeserialize(using = FileKeyVoDeserializer.class)
    private String image2;

    /**
     * 图片3
     */
    @JsonDeserialize(using = FileKeyVoDeserializer.class)
    private String image3;

    /**
     * 图片4
     */
    @JsonDeserialize(using = FileKeyVoDeserializer.class)
    private String image4;


}