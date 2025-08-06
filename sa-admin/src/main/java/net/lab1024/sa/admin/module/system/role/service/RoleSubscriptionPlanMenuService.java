package net.lab1024.sa.admin.module.system.role.service;

import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.module.system.menu.domain.vo.MenuSimpleTreeVO;
import net.lab1024.sa.admin.module.system.role.dao.RoleDao;
import net.lab1024.sa.admin.module.system.role.dao.RoleSubscriptionPlanMenuDao;
import net.lab1024.sa.admin.module.system.role.domain.entity.RoleBaseEntity;
import net.lab1024.sa.admin.module.system.role.domain.entity.RoleMenuEntity;
import net.lab1024.sa.admin.module.system.role.domain.form.RoleMenuUpdateForm;
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleMenuTreeVO;
import net.lab1024.sa.admin.module.system.role.manager.RoleSubscriptionPlanMenuManager;
import net.lab1024.sa.admin.module.system.subscriptionplanmenu.dao.SubscriptionPlanMenuDao;
import net.lab1024.sa.admin.module.system.subscriptionplanmenu.domain.entity.SubscriptionPlanMenuBaseEntity;
import net.lab1024.sa.admin.module.system.subscriptionplanmenu.domain.vo.SubscriptionPlanMenuVO;
import net.lab1024.sa.base.common.code.UserErrorCode;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 角色-菜单
 *
 * @Author 1024创新实验室: 善逸
 * @Date 2021-10-22 23:17:47
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Service
public class RoleSubscriptionPlanMenuService {

    @Resource
    private RoleDao roleDao;
    @Resource
    private RoleSubscriptionPlanMenuDao roleSubscriptionPlanMenuDao;
    @Resource
    private RoleSubscriptionPlanMenuManager roleSubscriptionPlanMenuManager;
    @Resource
    private SubscriptionPlanMenuDao subscriptionPlanMenuDao;

    /**
     * 更新角色权限
     *
     */
    public ResponseDTO<String> updateRoleMenu(RoleMenuUpdateForm roleMenuUpdateForm) {
        //查询角色是否存在
        Long roleId = roleMenuUpdateForm.getRoleId();
        RoleBaseEntity roleEntity = roleDao.selectById(roleId);
        if (null == roleEntity) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }
        List<RoleMenuEntity> roleMenuEntityList = Lists.newArrayList();
        RoleMenuEntity roleMenuEntity;
        for (Long menuId : roleMenuUpdateForm.getMenuIdList()) {
            roleMenuEntity = new RoleMenuEntity();
            roleMenuEntity.setRoleId(roleId);
            roleMenuEntity.setMenuId(menuId);
            roleMenuEntityList.add(roleMenuEntity);
        }
        roleSubscriptionPlanMenuManager.updateRoleMenu(roleMenuUpdateForm.getRoleId(), roleMenuEntityList);
        return ResponseDTO.ok();
    }

    /**
     * 根据角色id集合，查询其所有的菜单权限
     *
     */
    public List<SubscriptionPlanMenuVO> getMenuList(List<Long> roleIdList, Boolean administratorFlag, Long companyId) {
        //管理员返回该公司所拥有的所有菜单
        if(administratorFlag){
            List<SubscriptionPlanMenuBaseEntity> menuEntityList = roleSubscriptionPlanMenuDao.selectMenuListByRoleIdList(Lists.newArrayList(), false,companyId);
            return SmartBeanUtil.copyList(menuEntityList, SubscriptionPlanMenuVO.class);
        }
        //非管理员 无角色 返回空菜单
        if (CollectionUtils.isEmpty(roleIdList)) {
            return new ArrayList<>();
        }
        List<SubscriptionPlanMenuBaseEntity> menuEntityList = roleSubscriptionPlanMenuDao.selectMenuListByRoleIdList(roleIdList, false,companyId);
        return SmartBeanUtil.copyList(menuEntityList, SubscriptionPlanMenuVO.class);
    }


    /**
     * 获取角色关联菜单权限
     *
     */
    public ResponseDTO<RoleMenuTreeVO> getRoleSelectedMenu(Long roleId) {
        RoleMenuTreeVO res = new RoleMenuTreeVO();
        res.setRoleId(roleId);
        //查询角色ID选择的菜单权限
        List<Long> selectedMenuId = roleSubscriptionPlanMenuDao.queryMenuIdByRoleId(roleId);
        res.setSelectedMenuId(selectedMenuId);
        //查询菜单权限
        List<SubscriptionPlanMenuVO> subscriptionPlanMenuVOList = subscriptionPlanMenuDao.queryMenuList(Boolean.FALSE, Boolean.FALSE, null, null);
        Map<Long, List<SubscriptionPlanMenuVO>> parentMap = subscriptionPlanMenuVOList.stream()
                .collect(Collectors.groupingBy(SubscriptionPlanMenuVO::getParentId, Collectors.toList()));
        List<MenuSimpleTreeVO> menuTreeList = this.buildMenuTree(parentMap, NumberUtils.LONG_ZERO);
        res.setMenuTreeList(menuTreeList);
        return ResponseDTO.ok(res);
    }

    /**
     * 构建菜单树
     *
     */
    private List<MenuSimpleTreeVO> buildMenuTree(Map<Long, List<SubscriptionPlanMenuVO>> parentMap, Long parentId) {
        // 获取本级菜单树List
        List<MenuSimpleTreeVO> res = parentMap.getOrDefault(parentId, Lists.newArrayList()).stream()
                .map(e -> SmartBeanUtil.copy(e, MenuSimpleTreeVO.class)).collect(Collectors.toList());
        // 循环遍历下级菜单
        res.forEach(e -> {
            e.setChildren(this.buildMenuTree(parentMap, e.getMenuId()));
        });
        return res;
    }
}
