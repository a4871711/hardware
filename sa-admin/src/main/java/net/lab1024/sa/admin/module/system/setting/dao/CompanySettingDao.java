package net.lab1024.sa.admin.module.system.setting.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.setting.domain.entity.CompanySettingEntity;
import net.lab1024.sa.admin.module.system.setting.domain.form.CompanySettingQueryForm;
import net.lab1024.sa.admin.module.system.setting.domain.vo.CompanySettingVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 系统参数设置-公司表 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-04-20 17:30:25
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface CompanySettingDao extends BaseMapper<CompanySettingEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<CompanySettingVO> queryPage(Page page, @Param("queryForm") CompanySettingQueryForm queryForm);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("companySettingId") Long companySettingId, @Param("deletedFlag") boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList") List<Long> idList, @Param("deletedFlag") boolean deletedFlag);

}
