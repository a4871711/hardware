package net.lab1024.sa.base.module.support.serialnumber.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 公司自定义单号生成器定义表 实体类
 *
 * @Author 赵嘉伟
 * @Date 2025-04-08 19:02:04
 * @Copyright 赵嘉伟
 */

@Data
@TableName("t_company_serial_number")
public class CompanySerialNumberEntity {

    /**
     * 公司自定义的单号生成器
     */
    @TableId
    private Long companySerialNumberId;

    /**
     * 单号生成器
     */
    private Integer serialNumberId;

    /**
     * 格式[yyyy]表示年,[mm]标识月,[dd]表示日,[nnn]表示三位数字
     */
    private String format;

    /**
     * 规则格式。none没有周期, year 年周期, month月周期, day日周期
     */
    private String ruleType;

    /**
     * 初始值
     */
    private Long initNumber;

    /**
     * 步长随机数
     */
    private Integer stepRandomRange;

    /**
     * 公司id
     */
    private Long companyId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 上次产生的单号, 默认为空
     */
    private Long lastNumber;

    /**
     * 上次产生的单号时间
     */
    private LocalDateTime lastTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
