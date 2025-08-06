package net.lab1024.sa.admin.module.business.base.componentdict.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

/**
 * 系列组件表 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-05-01 18:07:45
 * @Copyright 赵嘉伟
 */

@Data
@TableName("t_component_dict")
public class ComponentDictEntity extends CompanyBaseEntity {

    /**
     * 主键
     */
    @TableId
    private Long componentSeriesId;

    /**
     * 类型(系列、其他等等)
     */
    private String typeCode;

    /**
     * 数据项编码
     */
    private String itemCode;

    /**
     * 数据项名称
     */
    private String itemName;

    /**
     * 备注
     */
    private String remark;

}
