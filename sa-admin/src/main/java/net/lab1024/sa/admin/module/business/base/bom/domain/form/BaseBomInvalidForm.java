package net.lab1024.sa.admin.module.business.base.bom.domain.form;

import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 多级BOM-主表 审核/反审核 表单
 *
 * @Author 赵嘉伟
 * @Date 2025-04-25 23:33:04
 * @Copyright 赵嘉伟
 */

@Data
public class BaseBomInvalidForm {

    @TableId
    @NotNull(message = "主键 不能为空")
    private Long bomId;

    @NotNull(message = "作废/反作废 不能为空")
    private Boolean invalid;

}