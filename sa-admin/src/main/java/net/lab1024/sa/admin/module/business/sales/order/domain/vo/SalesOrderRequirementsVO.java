package net.lab1024.sa.admin.module.business.sales.order.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.json.serializer.FileKeyVoSerializer;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

/**
 * 销售订单-订单要求表 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-05-25 19:36:51
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class SalesOrderRequirementsVO extends CompanyBaseEntity {


    @Schema(description = "销售订单-订单要求表id")
    private Long salesOrderRequirementsId;

    private Long bomId;

    @Schema(description = "销售订货单id")
    private Long salesOrderId;

    @Schema(description = "要求类别")
    private String requirementType;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "图片1")
    @JsonSerialize(using = FileKeyVoSerializer.class)
    private String image1;

    @Schema(description = "图片2")
    @JsonSerialize(using = FileKeyVoSerializer.class)
    private String image2;

    @Schema(description = "图片3")
    @JsonSerialize(using = FileKeyVoSerializer.class)
    private String image3;

    @Schema(description = "图片4")
    @JsonSerialize(using = FileKeyVoSerializer.class)
    private String image4;

    @Schema(description = "备注")
    private String remark;

    private String productCode;

    private String productName;

}
