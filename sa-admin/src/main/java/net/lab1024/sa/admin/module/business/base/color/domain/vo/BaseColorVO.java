package net.lab1024.sa.admin.module.business.base.color.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.base.color.domain.entity.BaseColorEntity;

/**
 * 颜色资料表 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-04-20 15:48:55
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseColorVO extends BaseColorEntity {


    @Schema(description = "颜色资料id")
    private Long colorId;

    @Schema(description = "颜色编号")
    private String colorCode;

    @Schema(description = "颜色名称")
    private String colorName;

    @Schema(description = "英文颜色")
    private String colorEngName;

    @Schema(description = "是否被禁用")
    private Boolean disabledFlag;

    @Schema(description = "备注")
    private String remark;

}
