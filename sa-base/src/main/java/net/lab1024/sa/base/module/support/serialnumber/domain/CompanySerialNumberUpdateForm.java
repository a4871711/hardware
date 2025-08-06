package net.lab1024.sa.base.module.support.serialnumber.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description TODO
 * @Author 赵嘉伟
 * @Date 20:35 2025/4/9
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class CompanySerialNumberUpdateForm extends CompanySerialNumberEntity {

    private Long companySerialNumberId;

    /**
     * 单号生成器
     */
    @NotNull(message = "单号生成器不能为空")
    private Integer serialNumberId;
}
