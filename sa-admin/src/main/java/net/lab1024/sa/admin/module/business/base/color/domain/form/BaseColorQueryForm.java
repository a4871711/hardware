package net.lab1024.sa.admin.module.business.base.color.domain.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.domain.PageParam;

/**
 * 颜色资料表 分页查询表单
 *
 * @Author 赵嘉伟
 * @Date 2025-04-20 15:48:55
 * @Copyright 赵嘉伟
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class BaseColorQueryForm extends PageParam {

    private String keywords;
}
