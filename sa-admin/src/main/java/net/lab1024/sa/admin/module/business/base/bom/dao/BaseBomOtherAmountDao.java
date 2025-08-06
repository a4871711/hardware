package net.lab1024.sa.admin.module.business.base.bom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.base.bom.domain.entity.BaseBomOtherAmountEntity;
import net.lab1024.sa.admin.module.business.base.bom.domain.form.BaseBomOtherAmountQueryForm;
import net.lab1024.sa.admin.module.business.base.bom.domain.vo.BaseBomOtherAmountVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 多级bom其他费用 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-04-30 21:05:57
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface BaseBomOtherAmountDao extends BaseMapper<BaseBomOtherAmountEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<BaseBomOtherAmountVO> queryPage(Page page, @Param("queryForm") BaseBomOtherAmountQueryForm queryForm);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("bomOtherAmount") Long bomOtherAmount, @Param("deletedFlag") boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList") List<Long> idList, @Param("deletedFlag") boolean deletedFlag);

    List<BaseBomOtherAmountVO> findByBomId(Long bomId);
}
