package net.lab1024.sa.admin.module.business.base.bom.domain.form;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.base.bom.domain.entity.BaseBomEntity;
import net.lab1024.sa.base.common.json.deserializer.FileKeyVoDeserializer;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.util.List;

/**
 * 多级BOM-主表 更新表单
 *
 * @Author 赵嘉伟
 * @Date 2025-04-25 23:33:04
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseBomUpdateForm extends BaseBomEntity {

    /**
     * 产品编号
     */
    @NotBlank(message = "产品编号不能为空")
    private String productCode;

    /**
     * 产品名称
     */
    @NotBlank(message = "产品名称不能为空")
    private String productName;

    @JsonDeserialize(using = FileKeyVoDeserializer.class)
    private String productImage;

    /**
     * 产品材料
     */
    private List<BaseBomProductUpdateForm> bomProductList;

    /**
     * 其他费用
     */
    private List<BaseBomOtherAmountUpdateForm> bomOtherAmountList;
}