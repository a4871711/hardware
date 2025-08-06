package net.lab1024.sa.admin.module.business.base.cust.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.domain.PageParam;

/**
 * 客商资料 分页查询表单
 *
 * @Author 赵嘉伟
 * @Date 2025-03-27 23:10:41
 * &#064;Copyright  赵嘉伟
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class BaseCustQueryForm extends PageParam {

    @Schema(description = "关键字")
    private String keywords;

    @Schema(description = "客商类型")
    private String custTypeCode;
}
