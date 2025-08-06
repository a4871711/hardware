package net.lab1024.sa.admin.module.business.base.mat.mat.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.base.mat.mat.domain.entity.BaseMatEntity;
import net.lab1024.sa.admin.module.business.base.mat.matcust.domain.form.BaseMatCustAddForm;

import java.util.List;

/**
 * 物料资料表 更新表单
 *
 * @Author 赵嘉伟
 * @Date 2025-04-01 22:25:04
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseMatUpdateForm extends BaseMatEntity {

    @Schema(description = "物料id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "物料id 不能为空")
    private Long matId;

    @Schema(description = "版本号(乐观锁)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "版本号(乐观锁) 不能为空")
    private Integer version;

    /**
     * 物料编号
     */
    @Schema(description = "物料编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "物料编号不能为空")
    private String matNo;

    /**
     * 物料名称
     */
    @Schema(description = "物料名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "物料名称不能为空")
    private String matName;

    @Schema(description = "物料分类id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "物料分类id 不能为空")
    private Long matTypeId;

    @Schema(description = "供应商单价页版")
    @Valid
    private List<BaseMatCustAddForm> baseMatCustList;

}