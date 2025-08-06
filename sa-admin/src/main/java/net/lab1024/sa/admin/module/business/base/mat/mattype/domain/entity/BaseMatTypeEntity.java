package net.lab1024.sa.admin.module.business.base.mat.mattype.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

/**
 * 物料分类表 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-04-01 22:25:10
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_base_mat_type")
public class BaseMatTypeEntity extends CompanyBaseEntity {

    /**
     * 物料分类id
     */
    @TableId
    private Long matTypeId;

    /**
     * 分类编号
     */
    private String matTypeCode;

    /**
     * 分类名称
     */
    private String matTypeName;

    /**
     * 上级分类id
     */
    private Long matTypeParentId;

    /**
     * 备注
     */
    private String remark;

}
