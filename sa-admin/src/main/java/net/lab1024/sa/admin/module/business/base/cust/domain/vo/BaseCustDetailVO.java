package net.lab1024.sa.admin.module.business.base.cust.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lab1024.sa.admin.module.business.base.bank.domain.vo.BaseBankVO;
import net.lab1024.sa.base.common.json.deserializer.DictValueVoDeserializer;
import net.lab1024.sa.base.common.mybatisplus.domain.entity.CompanyBaseEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * 客商资料 列表VO
 *
 * @Author 赵嘉伟
 * @Date 2025-03-27 23:10:41
 * @Copyright 赵嘉伟
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseCustDetailVO extends CompanyBaseEntity {


    @Schema(description = "主键")
    private Long custId;

    @Schema(description = "客商编号")
    private String custNo;

    @Schema(description = "客商名称")
    private String custName;

    @Schema(description = "客商分类")
    private Long custTypeId;

    @Schema(description = "扣税类型")
    @JsonDeserialize(using = DictValueVoDeserializer.class)
    private String taxType;

    @Schema(description = "税点")
    private BigDecimal taxRate;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "送货地址")
    private String shippingAddress;

    @Schema(description = "联系人")
    private String contactPerson;

    @Schema(description = "联系人电话")
    private String contactPhone;

    @Schema(description = "结算方式")
    private String paymentMethod;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "客商类型编码")
    private String custTypeCode;

    @Schema(description = "客商类型名称")
    private String custTypeName;

    @Schema(description = "银行信息")
    private List<BaseBankVO> bankEntityList;
}
