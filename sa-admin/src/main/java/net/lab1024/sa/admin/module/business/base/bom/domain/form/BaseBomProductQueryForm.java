package net.lab1024.sa.admin.module.business.base.bom.domain.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.domain.PageParam;

/**
 * 多级BOM-产品材料 分页查询表单
 *
 * @Author 赵嘉伟
 * @Date 2025-04-30 21:05:57
 * @Copyright 赵嘉伟
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class BaseBomProductQueryForm extends PageParam {

    private String keywords;
}
