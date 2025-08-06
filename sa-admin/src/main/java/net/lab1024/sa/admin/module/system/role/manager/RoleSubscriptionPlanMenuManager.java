package net.lab1024.sa.admin.module.system.role.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.module.system.role.dao.RoleSubscriptionPlanMenuDao;
import net.lab1024.sa.admin.module.system.role.domain.entity.RoleMenuEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @className: RoleSubscriptionPlanMenuManager
 * @author: D7608
 * @date: 2025/3/21 14:00
 * @description:
 */
@Service
public class RoleSubscriptionPlanMenuManager extends ServiceImpl<RoleSubscriptionPlanMenuDao, RoleMenuEntity> {

    @Resource
    private RoleSubscriptionPlanMenuDao roleSubscriptionPlanMenuDao;

    /**
     * 更新角色权限
     *
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateRoleMenu(Long roleId, List<RoleMenuEntity> roleMenuEntityList) {
        // 根据角色ID删除菜单权限
        roleSubscriptionPlanMenuDao.deleteByRoleId(roleId);
        // 批量添加菜单权限
        saveBatch(roleMenuEntityList);
    }
}
