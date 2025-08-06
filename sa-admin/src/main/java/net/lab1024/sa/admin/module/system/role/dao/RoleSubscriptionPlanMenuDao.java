package net.lab1024.sa.admin.module.system.role.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.system.role.domain.entity.RoleMenuEntity;
import net.lab1024.sa.admin.module.system.subscriptionplanmenu.domain.entity.SubscriptionPlanMenuBaseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RoleSubscriptionPlanMenuDao extends BaseMapper<RoleMenuEntity> {

    /**
     * 根据角色ID删除菜单权限
     *
     */
    int deleteByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据角色ID集合查询选择的菜单权限
     *
     */
    List<SubscriptionPlanMenuBaseEntity> selectMenuListByRoleIdList(@Param("roleIdList") List<Long> roleIdList,
                                                                    @Param("deletedFlag") Boolean deletedFlag,
                                                                    @Param("companyId") Long companyId
    );

    /**
     * 根据角色ID查询选择的菜单权限
     *
     */
    List<Long> queryMenuIdByRoleId(@Param("roleId") Long roleId);



}
