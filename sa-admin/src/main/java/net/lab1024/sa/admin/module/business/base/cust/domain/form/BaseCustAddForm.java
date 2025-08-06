package net.lab1024.sa.admin.module.business.base.cust.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.base.cust.domain.entity.BaseCustEntity;

import java.util.Set;

/**
 * 客商资料 新建表单
 *
 * @Author 赵嘉伟
 * @Date 2025-03-27 23:10:41
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseCustAddForm extends BaseCustEntity {

    @Schema(description = "客商编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "客商编号 不能为空")
    private String custNo;

    @Schema(description = "客商名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "客商名称 不能为空")
    private String custName;

    @Schema(description = "银行账号id，往中间表中插入")
    private Set<Long> bankIdList;
}