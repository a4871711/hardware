package net.lab1024.sa.admin.module.system.subscriptionplanmenu.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.module.system.menu.constant.MenuTypeEnum;
import net.lab1024.sa.admin.module.system.subscriptionplanmenu.dao.SubscriptionPlanMenuDao;
import net.lab1024.sa.admin.module.system.subscriptionplanmenu.domain.entity.SubscriptionPlanMenuBaseEntity;
import net.lab1024.sa.admin.module.system.subscriptionplanmenu.domain.form.SubscriptionPlanMenuAddForm;
import net.lab1024.sa.admin.module.system.subscriptionplanmenu.domain.form.SubscriptionPlanMenuBaseForm;
import net.lab1024.sa.admin.module.system.subscriptionplanmenu.domain.form.SubscriptionPlanMenuMoveForm;
import net.lab1024.sa.admin.module.system.subscriptionplanmenu.domain.form.SubscriptionPlanMenuUpdateForm;
import net.lab1024.sa.admin.module.system.subscriptionplanmenu.domain.vo.SubscriptionPlanMenuTreeVO;
import net.lab1024.sa.admin.module.system.subscriptionplanmenu.domain.vo.SubscriptionPlanMenuVO;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 订阅计划菜单权限 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-03-11 08:17:27
 * @Copyright 赵嘉伟
 */

@Service
public class SubscriptionPlanMenuService {

    @Resource
    private SubscriptionPlanMenuDao subscriptionPlanMenuDao;

    /**
     * 批量删除
     *
     * @param menuIdList
     * @return
     */
    public ResponseDTO<String> batchDelete(List<Long> menuIdList) {
        if (CollectionUtils.isEmpty(menuIdList)) {
            return ResponseDTO.userErrorParam("所选菜单不能为空");
        }
        subscriptionPlanMenuDao.batchUpdateDeleted(menuIdList, true);
        // 孩子节点也需要删除
        this.recursiveDeleteChildren(menuIdList);
        return ResponseDTO.ok();
    }

    private void recursiveDeleteChildren(List<Long> menuIdList) {
        List<Long> childrenMenuIdList = subscriptionPlanMenuDao.selectMenuIdByParentIdList(menuIdList);
        if (CollectionUtil.isEmpty(childrenMenuIdList)) {
            return;
        }
        subscriptionPlanMenuDao.batchUpdateDeleted(childrenMenuIdList, true);
        recursiveDeleteChildren(childrenMenuIdList);
    }

    /**
     * 查询菜单列表
     *
     * @param subscriptionPlan 订阅计划ID
     * @return List<SubscriptionPlanMenuVOTemp> 菜单列表
     */
    public List<SubscriptionPlanMenuVO> queryList(Integer subscriptionPlan) {
        List<SubscriptionPlanMenuVO> menuVOList = subscriptionPlanMenuDao.queryMenuList(Boolean.FALSE, null, null,
                subscriptionPlan);
        // 根据ParentId进行分组
        Map<Long, List<SubscriptionPlanMenuVO>> parentMap = menuVOList.stream()
                .collect(Collectors.groupingBy(SubscriptionPlanMenuVO::getParentId, Collectors.toList()));
        return this.filterNoParentMenu(parentMap, NumberUtils.LONG_ZERO);
    }

    public ResponseDTO<List<SubscriptionPlanMenuTreeVO>> queryMenuTree(Boolean onlyMenu, Integer subscriptionPlan) {
        List<Integer> menuTypeList = Lists.newArrayList();
        if (onlyMenu) {
            menuTypeList = Lists.newArrayList(MenuTypeEnum.CATALOG.getValue(), MenuTypeEnum.MENU.getValue());
        }
        List<SubscriptionPlanMenuVO> menuVOList = subscriptionPlanMenuDao.queryMenuList(Boolean.FALSE, null,
                menuTypeList, subscriptionPlan);
        // 根据ParentId进行分组
        Map<Long, List<SubscriptionPlanMenuVO>> parentMap = menuVOList.stream()
                .collect(Collectors.groupingBy(SubscriptionPlanMenuVO::getParentId, Collectors.toList()));
        List<SubscriptionPlanMenuTreeVO> menuTreeVOList = this.buildMenuTree(parentMap, NumberUtils.LONG_ZERO);
        return ResponseDTO.ok(menuTreeVOList);
    }

    public synchronized ResponseDTO<String> addMenu(SubscriptionPlanMenuAddForm menuAddForm) {
        // 校验菜单名称
        if (this.validateMenuName(menuAddForm)) {
            return ResponseDTO.userErrorParam("菜单名称已存在");
        }
        // 校验前端权限字符串
        if (this.validateWebPerms(menuAddForm)) {
            return ResponseDTO.userErrorParam("前端权限字符串已存在");
        }
        SubscriptionPlanMenuBaseEntity menuEntity = SmartBeanUtil.copy(menuAddForm,
                SubscriptionPlanMenuBaseEntity.class);
        subscriptionPlanMenuDao.insert(menuEntity);
        return ResponseDTO.ok();
    }

    List<SubscriptionPlanMenuTreeVO> buildMenuTree(Map<Long, List<SubscriptionPlanMenuVO>> parentMap, Long parentId) {
        // 获取本级菜单树List
        List<SubscriptionPlanMenuTreeVO> res = parentMap.getOrDefault(parentId, Lists.newArrayList()).stream()
                .map(e -> SmartBeanUtil.copy(e, SubscriptionPlanMenuTreeVO.class)).collect(Collectors.toList());
        // 循环遍历下级菜单
        res.forEach(e -> {
            e.setChildren(this.buildMenuTree(parentMap, e.getMenuId()));
        });
        return res;
    }

    private List<SubscriptionPlanMenuVO> filterNoParentMenu(Map<Long, List<SubscriptionPlanMenuVO>> parentMap,
                                                            Long parentId) {
        // 获取本级菜单树List
        List<SubscriptionPlanMenuVO> res = parentMap.getOrDefault(parentId, Lists.newArrayList());
        List<SubscriptionPlanMenuVO> childMenu = Lists.newArrayList();
        // 循环遍历下级菜单
        res.forEach(e -> {
            List<SubscriptionPlanMenuVO> menuList = this.filterNoParentMenu(parentMap, e.getMenuId());
            childMenu.addAll(menuList);
        });
        res.addAll(childMenu);
        return res;
    }

    public synchronized ResponseDTO<String> updateMenu(SubscriptionPlanMenuUpdateForm menuUpdateForm) {
        // 校验菜单是否存在
        SubscriptionPlanMenuBaseEntity selectMenu = subscriptionPlanMenuDao.selectById(menuUpdateForm.getMenuId());
        if (selectMenu == null) {
            return ResponseDTO.userErrorParam("菜单不存在");
        }
        if (selectMenu.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("菜单已被删除");
        }
        // 校验菜单名称
        if (this.validateMenuName(menuUpdateForm)) {
            return ResponseDTO.userErrorParam("菜单名称已存在");
        }
        // 校验前端权限字符串
        if (this.validateWebPerms(menuUpdateForm)) {
            return ResponseDTO.userErrorParam("前端权限字符串已存在");
        }
        if (menuUpdateForm.getMenuId().equals(menuUpdateForm.getParentId())) {
            return ResponseDTO.userErrorParam("上级菜单不能为自己");
        }
        SubscriptionPlanMenuBaseEntity menuEntity = SmartBeanUtil.copy(menuUpdateForm,
                SubscriptionPlanMenuBaseEntity.class);
        subscriptionPlanMenuDao.updateById(menuEntity);
        return ResponseDTO.ok();
    }

    /**
     * 校验前端权限字符串
     *
     * @return true 重复 false 未重复
     */
    public <T extends SubscriptionPlanMenuBaseForm> Boolean validateWebPerms(T menuDTO) {
        LambdaQueryWrapper<SubscriptionPlanMenuBaseEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SubscriptionPlanMenuBaseEntity::getWebPerms, menuDTO.getWebPerms());
        wrapper.eq(SubscriptionPlanMenuBaseEntity::getDeletedFlag, false);
        SubscriptionPlanMenuBaseEntity menu = subscriptionPlanMenuDao.selectOne(wrapper);
        if (menuDTO instanceof SubscriptionPlanMenuAddForm) {
            return menu != null;
        }
        if (menuDTO instanceof SubscriptionPlanMenuUpdateForm subscriptionplanmenuupdateform) {
            Long menuId = subscriptionplanmenuupdateform.getMenuId();
            return menu != null && menu.getMenuId().longValue() != menuId.longValue();
        }
        return true;
    }

    public <T extends SubscriptionPlanMenuBaseForm> Boolean validateMenuName(T menuDTO) {
        LambdaQueryWrapper<SubscriptionPlanMenuBaseEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SubscriptionPlanMenuBaseEntity::getMenuName, menuDTO.getMenuName())
                .eq(SubscriptionPlanMenuBaseEntity::getParentId, menuDTO.getParentId())
                .eq(SubscriptionPlanMenuBaseEntity::getDeletedFlag, Boolean.FALSE);

        SubscriptionPlanMenuBaseEntity menu = subscriptionPlanMenuDao.selectOne(queryWrapper);

        if (menuDTO instanceof SubscriptionPlanMenuAddForm) {
            return menu != null;
        }
        if (menuDTO instanceof SubscriptionPlanMenuUpdateForm subscriptionplanmenuupdateform) {
            Long menuId = subscriptionplanmenuupdateform.getMenuId();
            return menu != null && menu.getMenuId().longValue() != menuId.longValue();
        }
        return true;
    }

    public List<SubscriptionPlanMenuBaseEntity> getBySubscriptionPlan(Integer subscriptionPlan) {
        LambdaQueryWrapper<SubscriptionPlanMenuBaseEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.le(SubscriptionPlanMenuBaseEntity::getSubscriptionPlan, subscriptionPlan);
        return subscriptionPlanMenuDao.selectList(queryWrapper);
    }

    /*
     * 移动菜单位置，将订阅计划是xx的菜单移动到xx菜单
     */
    @Transactional(rollbackFor = Throwable.class)
    public ResponseDTO<String> batchMove(SubscriptionPlanMenuMoveForm menuMoveForm) {
        List<Long> menuIdList = menuMoveForm.getMenuIdList();
        if (menuIdList == null || menuIdList.isEmpty()) {
            return ResponseDTO.userErrorParam("菜单ID列表为空");
        }

        // **1. 批量查询所有菜单**
        List<SubscriptionPlanMenuBaseEntity> menuEntities = subscriptionPlanMenuDao.selectBatchIds(menuIdList);
        if (menuEntities.size() != menuIdList.size()) {
            return ResponseDTO.userErrorParam("部分菜单不存在或已删除");
        }

        // **2. 预处理 ParentId 相关信息，避免 N 次查询**
        Map<Long, SubscriptionPlanMenuBaseEntity> menuMap = menuEntities.stream()
                .collect(Collectors.toMap(SubscriptionPlanMenuBaseEntity::getMenuId, entity -> entity));

        List<SubscriptionPlanMenuBaseEntity> toUpdate = new ArrayList<>();

        for (SubscriptionPlanMenuBaseEntity menuBaseEntity : menuEntities) {
            if (Boolean.TRUE.equals(menuBaseEntity.getDeletedFlag())) {
                return ResponseDTO.userErrorParam("菜单不存在");
            }

            Long parentId = menuBaseEntity.getParentId();
            if (parentId != 0 && !menuIdList.contains(parentId)) {
                SubscriptionPlanMenuBaseEntity parentMenu = menuMap.get(parentId);
                if (parentMenu != null && parentMenu.getSubscriptionPlan() > menuMoveForm.getSubscriptionPlanMenu()) {
                    return ResponseDTO.userErrorParam("该菜单的父菜单的订阅计划 大于 他要移动的订阅计划");
                }
            }

            // **3. 直接修改实体对象，稍后统一批量更新**
            menuBaseEntity.setSubscriptionPlan(menuMoveForm.getSubscriptionPlanMenu());
            toUpdate.add(menuBaseEntity);
        }
        // **4. 批量更新**
        if (!toUpdate.isEmpty()) {
            subscriptionPlanMenuDao.insertOrUpdate(toUpdate);
        }
        return ResponseDTO.ok();
    }
}
