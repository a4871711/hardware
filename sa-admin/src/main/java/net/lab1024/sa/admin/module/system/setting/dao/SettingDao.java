package net.lab1024.sa.admin.module.system.setting.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.setting.domain.entity.SettingEntity;
import net.lab1024.sa.admin.module.system.setting.domain.form.SettingQueryForm;
import net.lab1024.sa.admin.module.system.setting.domain.vo.SettingVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 系统参数设置-主表 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-04-20 17:30:24
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface SettingDao extends BaseMapper<SettingEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<SettingVO> queryPage(Page page,
                              @Param("queryForm") SettingQueryForm queryForm,
                              @Param("companyId") Long companyId);

    SettingVO findById(@Param("settingId") Long settingId, @Param("companyId") Long companyId);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("settingId") Long settingId, @Param("deletedFlag") boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList") List<Long> idList, @Param("deletedFlag") boolean deletedFlag);

}
