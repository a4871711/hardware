package net.lab1024.sa.admin.module.business.base.bank.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.base.bank.domain.entity.BaseBankEntity;
import net.lab1024.sa.base.common.json.serializer.DictValueVoSerializer;

/**
 * 银行信息 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-03-28 21:27:28
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseBankVO extends BaseBankEntity {


    @Schema(description = "主键")
    private Long bankId;

    @Schema(description = "银行账号")
    private String accountNo;

    @Schema(description = "账号名称")
    private String accountName;

    @Schema(description = "账号类型(公/私) 账号")
    @JsonSerialize(using = DictValueVoSerializer.class)
    private String accountType;

    @Schema(description = "是否被禁用 0否1是")
    private Integer disabledFlag;
}
