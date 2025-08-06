package net.lab1024.sa.admin.module.system.subscriptionplanmenu.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Description TODO
 * @Author 赵嘉伟
 * @Date 21:47 2025/3/17
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SubscriptionPlanMenuTreeVO extends SubscriptionPlanMenuVO {

    @Schema(description = "菜单子集")
    private List<SubscriptionPlanMenuTreeVO> children;

}
