package net.lab1024.sa.admin.module.business.base.bank.domain.form;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.domain.PageParam;
import net.lab1024.sa.base.common.json.deserializer.DictValueVoDeserializer;

/**
 * 银行信息 分页查询表单
 *
 * @Author 赵嘉伟
 * @Date 2025-03-28 21:27:28
 * @Copyright 赵嘉伟
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class BaseBankQueryForm extends PageParam {


    @Schema(description = "银行账号")
    private String accountNo;

    @Schema(description = "账号名称")
    private String accountName;

    @JsonDeserialize(using = DictValueVoDeserializer.class)
    @Schema(description = "账号类型(公/私) 账号")
    private String accountType;
}
