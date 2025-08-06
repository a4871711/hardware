package net.lab1024.sa.admin.module.business.base.bank.domain.form;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.base.bank.domain.entity.BaseBankEntity;
import net.lab1024.sa.base.common.json.deserializer.DictValueVoDeserializer;

/**
 * 银行信息 新建表单
 *
 * @Author 赵嘉伟
 * @Date 2025-03-28 21:27:28
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseBankAddForm extends BaseBankEntity {

    @Schema(description = "银行账号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "银行账号 不能为空")
    private String accountNo;

    @Schema(description = "账号名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "账号名称 不能为空")
    private String accountName;

    @Schema(description = "账号类型(公/私) 账号")
    @JsonDeserialize(using = DictValueVoDeserializer.class)
    private String accountType;


}