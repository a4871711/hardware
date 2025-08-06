package net.lab1024.sa.admin.module.system.menu.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 菜单
 *
 * @Author 1024创新实验室: 善逸
 * @Date 2022-03-06 22:04:37
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class SubscriptionPlanMenuTreeVOTemp extends SubscriptionPlanMenuVOTemp {

    @Schema(description = "菜单子集")
    private List<SubscriptionPlanMenuTreeVOTemp> children;
}
