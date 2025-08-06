package net.lab1024.sa.admin.module.system.subscriptionplanmenu.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.system.subscriptionplanmenu.domain.form.SubscriptionPlanMenuAddForm;
import net.lab1024.sa.admin.module.system.subscriptionplanmenu.domain.form.SubscriptionPlanMenuMoveForm;
import net.lab1024.sa.admin.module.system.subscriptionplanmenu.domain.form.SubscriptionPlanMenuUpdateForm;
import net.lab1024.sa.admin.module.system.subscriptionplanmenu.domain.vo.SubscriptionPlanMenuTreeVO;
import net.lab1024.sa.admin.module.system.subscriptionplanmenu.domain.vo.SubscriptionPlanMenuVO;
import net.lab1024.sa.admin.module.system.subscriptionplanmenu.service.SubscriptionPlanMenuService;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartRequestUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订阅计划菜单权限 Controller
 *
 * @Author 赵嘉伟
 * @Date 2025-03-11 08:17:27
 * @Copyright 赵嘉伟
 */

@RestController
@Tag(name = "订阅计划菜单权限")
public class SubscriptionPlanMenuController {

    @Resource
    private SubscriptionPlanMenuService subscriptionPlanMenuService;

    @Operation(summary = "批量删除 @author 赵嘉伟")
    @GetMapping("/subscriptionPlanMenu/batchDelete")
    @SaCheckPermission("system:subscriptionPlanMenu:delete")
    public ResponseDTO<String> batchDelete(@RequestParam("menuIdList") List<Long> menuIdList) {
        return subscriptionPlanMenuService.batchDelete(menuIdList);
    }

    @Operation(summary = "查询菜单列表 @author 赵嘉伟")
    @GetMapping("/subscriptionPlanMenu/query")
    public ResponseDTO<List<SubscriptionPlanMenuVO>> queryList(@Param("subscriptionPlan") Integer subscriptionPlan) {
        return ResponseDTO.ok(subscriptionPlanMenuService.queryList(subscriptionPlan));
    }

    @Operation(summary = "查询菜单树 @author 赵嘉伟")
    @GetMapping("/subscriptionPlanMenu/tree")
    public ResponseDTO<List<SubscriptionPlanMenuTreeVO>> queryMenuTree(@Param("subscriptionPlan") Integer subscriptionPlan,
                                                                       @RequestParam("onlyMenu") Boolean onlyMenu) {
        return subscriptionPlanMenuService.queryMenuTree(onlyMenu, subscriptionPlan);
    }

    @Operation(summary = "更新菜单 @author 赵嘉伟")
    @PostMapping("/subscriptionPlanMenu/update")
    @SaCheckPermission("system:subscriptionPlanMenu:update")
    public ResponseDTO<String> updateMenu(@RequestBody @Valid SubscriptionPlanMenuUpdateForm menuUpdateForm) {
        menuUpdateForm.setUpdateUserId(SmartRequestUtil.getRequestUserId());
        return subscriptionPlanMenuService.updateMenu(menuUpdateForm);
    }

    @Operation(summary = "添加 @author 赵嘉伟")
    @PostMapping("/subscriptionPlanMenu/add")
    @SaCheckPermission("system:subscriptionPlanMenu:add")
    public ResponseDTO<String> add(@RequestBody @Valid SubscriptionPlanMenuAddForm addForm) {
        return subscriptionPlanMenuService.addMenu(addForm);
    }

    @Operation(summary = "移动菜单 @author 赵嘉伟")
    @PostMapping("/subscriptionPlanMenu/batchMove")
    @SaCheckPermission("system:subscriptionPlanMenu:move")
    public ResponseDTO<String> batchMove(@RequestBody @Valid SubscriptionPlanMenuMoveForm menuMoveForm) {
        return subscriptionPlanMenuService.batchMove(menuMoveForm);
    }

}
