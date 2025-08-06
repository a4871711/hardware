package net.lab1024.sa.admin.module.system.position.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

/**
 * 职务表 实体类
 *
 * @Author kaiyun
 * @Date 2024-06-23 23:31:38
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a>
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_position")
public class PositionBaseEntity extends CompanyBaseEntity {

    /**
     * 职务ID
     */
    @TableId(type = IdType.AUTO)
    private Long positionId;

    /**
     * 职务名称
     */
    private String positionName;

    /**
     * 职级
     */
    private String level;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

}