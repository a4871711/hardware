package net.lab1024.sa.admin.module.business.base.componentdict.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

/**
 * 系列组件表 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-05-01 18:07:45
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class ComponentDictVO extends CompanyBaseEntity {


    @Schema(description = "主键")
    private Long componentSeriesId;

    @Schema(description = "类型(系列、其他等等)")
    private String typeCode;

    @Schema(description = "数据项编码")
    private String itemCode;

    @Schema(description = "数据项名称")
    private String itemName;

    @Schema(description = "备注")
    private String remark;
}
