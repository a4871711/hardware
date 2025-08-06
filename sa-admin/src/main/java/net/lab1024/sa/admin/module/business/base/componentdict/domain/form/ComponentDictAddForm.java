package net.lab1024.sa.admin.module.business.base.componentdict.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.base.componentdict.domain.entity.ComponentDictEntity;

/**
 * 系列组件表 新建表单
 *
 * @Author 赵嘉伟
 * @Date 2025-05-01 18:07:45
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class ComponentDictAddForm extends ComponentDictEntity {

    @Schema(description = "数据项名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "数据项名称 不能为空")
    private String itemName;

    @Schema(description = "类型(系列、其他等等)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "类型(系列、其他等等) 不能为空")
    private String typeCode;


}