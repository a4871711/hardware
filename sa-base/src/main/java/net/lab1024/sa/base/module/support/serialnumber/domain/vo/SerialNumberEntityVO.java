package net.lab1024.sa.base.module.support.serialnumber.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.base.module.support.serialnumber.domain.SerialNumberEntity;

/**
 * @Description TODO
 * @Author 赵嘉伟
 * @Date 22:21 2025/4/9
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SerialNumberEntityVO extends SerialNumberEntity {

    /**
     * 公司自定义的单号生成器
     */
    @TableId
    private Long companySerialNumberId;

    private String menuName;

    private String businessName;
}
