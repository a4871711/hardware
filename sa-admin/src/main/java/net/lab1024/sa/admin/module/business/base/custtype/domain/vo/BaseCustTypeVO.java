package net.lab1024.sa.admin.module.business.base.custtype.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

/**
 * 客商类型 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-03-28 21:27:26
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseCustTypeVO extends CompanyBaseEntity {


    @Schema(description = "主键")
    private Long custTypeId;

    @Schema(description = "客商分类编码")
    private String custTypeCode;

    @Schema(description = "客商分类名称")
    private String custTypeName;

    @Schema(description = "上级id")
    private Long parentId;

    @Schema(description = "备注")
    private String remark;
}
