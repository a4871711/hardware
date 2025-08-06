package net.lab1024.sa.admin.module.business.base.bom.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.domain.PageParam;

/**
 * 多级BOM-主表 分页查询表单
 *
 * @Author 赵嘉伟
 * @Date 2025-04-25 23:33:04
 * @Copyright 赵嘉伟
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class BaseBomQueryForm extends PageParam {

    @Schema(description = "关键字")
    private String keywords;
}
