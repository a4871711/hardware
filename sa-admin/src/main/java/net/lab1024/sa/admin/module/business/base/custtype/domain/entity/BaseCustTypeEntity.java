package net.lab1024.sa.admin.module.business.base.custtype.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

/**
 * 客商类型 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-03-28 21:27:26
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_base_cust_type")
public class BaseCustTypeEntity extends CompanyBaseEntity {

    /**
     * 主键
     */
    @TableId
    private Long custTypeId;

    /**
     * 客商分类编码
     */
    private String custTypeCode;

    /**
     * 客商分类名称
     */
    private String custTypeName;

    /**
     * 上级id
     */
    private Long parentId;

    /**
     * 备注
     */
    private String remark;
}
