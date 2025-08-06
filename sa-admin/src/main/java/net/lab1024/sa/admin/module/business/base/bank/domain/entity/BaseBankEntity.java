package net.lab1024.sa.admin.module.business.base.bank.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.math.BigDecimal;

/**
 * 银行信息 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-03-28 21:27:28
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_base_bank")
public class BaseBankEntity extends CompanyBaseEntity {

    /**
     * 主键
     */
    @TableId
    private Long bankId;

    /**
     * 银行账号
     */
    private String accountNo;

    /**
     * 账号名称
     */
    private String accountName;

    /**
     * 账号类型(公/私) 账号
     */
    private String accountType;

    /**
     * 是否被禁用 0否1是
     */
    private Integer disabledFlag;


    /**
     * 开户行
     */
    private String accountBank;

    /**
     * 账户金额
     */
    private BigDecimal accountAmount;

    /**
     * 是否客户账户
     */
    private Boolean custAccount;

}
