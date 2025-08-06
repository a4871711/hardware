package net.lab1024.sa.admin.module.business.base.componentdict.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.base.componentdict.domain.entity.ComponentDictEntity;

/**
 * 系列组件表 更新表单
 *
 * @Author 赵嘉伟
 * @Date 2025-05-01 18:07:45
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class ComponentDictUpdateForm extends ComponentDictEntity {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "主键 不能为空")
    private Long componentSeriesId;

    @Schema(description = "版本号(乐观锁)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "版本号(乐观锁) 不能为空")
    private Integer version;

}