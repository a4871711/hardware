package net.lab1024.sa.admin.module.business.base.mat.mat.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.json.deserializer.DictValueVoDeserializer;
import net.lab1024.sa.base.common.json.deserializer.FileKeyVoDeserializer;
import net.lab1024.sa.base.common.json.serializer.FileKeyVoSerializer;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.math.BigDecimal;

/**
 * 物料资料表 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-04-01 22:25:04
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_base_mat")
public class BaseMatEntity extends CompanyBaseEntity {

    /**
     * 物料id
     */
    @TableId
    private Long matId;

    /**
     * 物料分类id
     */
    private Long matTypeId;

    /**
     * 物料编号
     */
    private String matNo;

    /**
     * 物料名称
     */
    private String matName;

    /**
     * 规格
     */
    private String pro;

    /**
     * 颜色
     */
    private Long colorId;

    /**
     * 计算单位
     */
    private String calculationUnit;

    /**
     * 采购单位
     */
    private String purchaseUnit;

    /**
     * 单位转换系数
     */
    private BigDecimal conversionFactor;

    /**
     * 来源属性
     */
    @JsonDeserialize(using = DictValueVoDeserializer.class)
    private String sourceProperty;

    /**
     * 图片
     */
    @JsonSerialize(using = FileKeyVoSerializer.class)
    @JsonDeserialize(using = FileKeyVoDeserializer.class)
    private String matImage;

    /**
     * 重量单位
     */
    private String weightUnit;

    /**
     * 重量系数
     */
    private String weightFactor;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否失效
     */
    private Boolean invalid;

}
