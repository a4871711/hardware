package net.lab1024.sa.admin.module.business.base.bom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.base.bom.domain.entity.BaseBomEntity;
import net.lab1024.sa.admin.module.business.base.bom.domain.form.BaseBomQueryForm;
import net.lab1024.sa.admin.module.business.base.bom.domain.vo.BaseBomVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 多级BOM-主表 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-04-25 23:33:04
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface BaseBomDao extends BaseMapper<BaseBomEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<BaseBomVO> queryPage(Page page, @Param("queryForm") BaseBomQueryForm queryForm);

    List<BaseBomVO> querySearch(Page page, @Param("queryForm") BaseBomQueryForm queryForm);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("bomId") Long bomId, @Param("deletedFlag") boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList") List<Long> idList, @Param("deletedFlag") boolean deletedFlag);

    BaseBomVO findById(@Param("bomId") Long bomId);


}
