package net.lab1024.sa.admin.module.business.base.mat.matcust.domain.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.domain.PageParam;

/**
 * 物料供应商单价表 分页查询表单
 *
 * @Author 赵嘉伟
 * @Date 2025-04-22 00:40:08
 * @Copyright 赵嘉伟
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class BaseMatCustQueryForm extends PageParam {

    private String keywords;

}
