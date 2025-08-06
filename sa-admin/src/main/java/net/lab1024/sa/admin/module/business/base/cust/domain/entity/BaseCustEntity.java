package net.lab1024.sa.admin.module.business.base.cust.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.json.deserializer.DictValueVoDeserializer;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.math.BigDecimal;

/**
 * 客商资料 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-03-27 23:10:41
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_base_cust")
public class BaseCustEntity extends CompanyBaseEntity {

    /**
     * 主键
     */
    @TableId
    private Long custId;

    /**
     * 客商编号
     */
    private String custNo;

    /**
     * 客商名称
     */
    private String custName;

    /**
     * 客商分类
     */
    private Long custTypeId;

    /**
     * 扣税类型
     */
    @JsonDeserialize(using = DictValueVoDeserializer.class)
    private String taxType;

    /**
     * 税点
     */
    private BigDecimal taxRate;

    /**
     * 地址
     */
    private String address;

    /**
     * 送货地址
     */
    private String shippingAddress;

    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 联系人电话
     */
    private String contactPhone;

    /**
     * 结算方式
     */
    private String paymentMethod;

    /**
     * 备注
     */
    private String remarks;
}
