package net.lab1024.sa.admin.module.business.base.bom.domain.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.base.bom.domain.entity.BaseBomProductEntity;

import java.util.List;

/**
 * 多级BOM-产品材料 更新表单
 *
 * @Author 赵嘉伟
 * @Date 2025-04-30 21:05:57
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseBomProductUpdateForm extends BaseBomProductEntity {

    private List<BaseBomProductUpdateForm> children;
}