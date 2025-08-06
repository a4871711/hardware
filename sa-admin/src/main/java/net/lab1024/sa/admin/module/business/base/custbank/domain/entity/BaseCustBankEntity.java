package net.lab1024.sa.admin.module.business.base.custbank.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 银行客商功能表 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-03-28 23:41:32
 * @Copyright 赵嘉伟
 */

@Data
@TableName("t_base_cust_bank")
public class BaseCustBankEntity {

    /**
     * 主键
     */
    @TableId
    private Long custBankId;

    /**
     * 客商主键id
     */
    private Long custId;

    /**
     * 银行id
     */
    private Long bankId;

    /**
     * 公司id(租户号)
     */
    private Long companyId;

    /**
     * 创建人
     */
    private Long createUserId;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
