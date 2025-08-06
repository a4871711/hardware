package net.lab1024.sa.admin.module.business.base.color.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

/**
 * 颜色资料表 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-04-20 15:48:55
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_base_color")
public class BaseColorEntity extends CompanyBaseEntity {

    /**
     * 颜色资料id
     */
    @TableId
    private Long colorId;

    /**
     * 颜色编号
     */
    private String colorCode;

    /**
     * 颜色名称
     */
    private String colorName;

    /**
     * 英文颜色
     */
    private String colorEngName;

    /**
     * 是否被禁用
     */
    private Boolean disabledFlag;

    /**
     * 备注
     */
    private String remark;

}
