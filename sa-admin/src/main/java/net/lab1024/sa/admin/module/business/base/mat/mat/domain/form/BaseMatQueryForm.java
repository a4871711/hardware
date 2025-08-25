package net.lab1024.sa.admin.module.business.base.mat.mat.domain.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.domain.PageParam;

/**
 * 物料资料表 分页查询表单
 *
 * @Author 赵嘉伟
 * @Date 2025-04-01 22:25:04
 * @Copyright 赵嘉伟
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class BaseMatQueryForm extends PageParam {

    private String keywords;

    private Boolean invalid;

    private Long matTypeId;
}
