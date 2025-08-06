package net.lab1024.sa.base.common.mybatisplus.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class CompanyBaseEntity extends LogBaseEntity {

    /**
     * 公司ID
     */
    @TableField(value = "company_id", updateStrategy = FieldStrategy.NEVER, fill = FieldFill.INSERT)
    private Long companyId;



}
