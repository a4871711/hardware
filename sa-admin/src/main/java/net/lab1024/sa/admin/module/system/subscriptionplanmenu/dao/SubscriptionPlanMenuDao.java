package net.lab1024.sa.admin.module.system.subscriptionplanmenu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.subscriptionplanmenu.domain.entity.SubscriptionPlanMenuBaseEntity;
import net.lab1024.sa.admin.module.system.subscriptionplanmenu.domain.form.SubscriptionPlanMenuQueryForm;
import net.lab1024.sa.admin.module.system.subscriptionplanmenu.domain.vo.SubscriptionPlanMenuVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 订阅计划菜单权限 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-03-11 08:17:27
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface SubscriptionPlanMenuDao extends BaseMapper<SubscriptionPlanMenuBaseEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<SubscriptionPlanMenuVO> queryPage(Page page, @Param("queryForm") SubscriptionPlanMenuQueryForm queryForm);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("menuId")Long menuId,@Param("deletedFlag")boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList")List<Long> idList,@Param("deletedFlag")boolean deletedFlag);

    List<SubscriptionPlanMenuVO> queryMenuList(@Param("deletedFlag") Boolean deletedFlag,
                                               @Param("disabledFlag") Boolean disabledFlag,
                                               @Param("menuTypeList") List<Integer> menuTypeList,
                                               @Param("subscriptionPlan") Integer subscriptionPlan);

    List<Long> selectMenuIdByParentIdList(@Param("menuIdList") List<Long> menuIdList);

}
