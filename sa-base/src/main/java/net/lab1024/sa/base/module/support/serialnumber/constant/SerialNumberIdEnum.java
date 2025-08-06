package net.lab1024.sa.base.module.support.serialnumber.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.lab1024.sa.base.common.enumeration.BaseEnum;

/**
 * 单据序列号 枚举
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-03-25 21:46:07
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@AllArgsConstructor
@Getter
public enum SerialNumberIdEnum implements BaseEnum {

    ORDER(1, "订单id"),

    CONTRACT(2, "合同id"),

    BASE_MAT_FOUR(3, "物料id"),
    BASE_MAT_SIX(4, "物料id-6位序号"),

    SALES_ORDER(5, "编码生成规则"),
    COLOR_SIX(6, "颜色id-6位序号"),
    PURCHASE_REQUEST_ORDER(7, "采购申请单"),
    PURCHASE_ORDER(8, "采购订货单"),

    ;

    private final Integer serialNumberId;

    private final String desc;

    @Override
    public Integer getValue() {
        return serialNumberId;
    }

    @Override
    public String toString() {
        return "SerialNumberIdEnum{" +
                "serialNumberId=" + serialNumberId +
                ", desc='" + desc + '\'' +
                '}';
    }
}
