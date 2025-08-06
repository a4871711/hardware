package net.lab1024.sa.admin.module.business.base.componentdict.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.base.componentdict.domain.entity.ComponentDictEntity;
import net.lab1024.sa.admin.module.business.base.componentdict.domain.form.ComponentDictQueryForm;
import net.lab1024.sa.admin.module.business.base.componentdict.domain.vo.ComponentDictVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 系列组件表 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-05-01 18:07:45
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface ComponentDictDao extends BaseMapper<ComponentDictEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<ComponentDictVO> queryPage(Page page, @Param("queryForm") ComponentDictQueryForm queryForm);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("componentSeriesId") Long componentSeriesId, @Param("deletedFlag") boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList") List<Long> idList, @Param("deletedFlag") boolean deletedFlag);

}
