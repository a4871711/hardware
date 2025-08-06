package net.lab1024.sa.admin.module.business.base.bom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.base.bom.domain.entity.BaseBomProductEntity;
import net.lab1024.sa.admin.module.business.base.bom.domain.form.BaseBomProductQueryForm;
import net.lab1024.sa.admin.module.business.base.bom.domain.vo.BaseBomProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 多级BOM-产品材料 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-04-30 21:05:57
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface BaseBomProductDao extends BaseMapper<BaseBomProductEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<BaseBomProductVO> queryPage(Page page, @Param("queryForm") BaseBomProductQueryForm queryForm);


    List<BaseBomProductVO> findByBomId(@Param("bomId") Long bomId);

    List<BaseBomProductVO> findByBomIdList(@Param("bomIdList") List<Long> bomIdList);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("bomProductId") Long bomProductId, @Param("deletedFlag") boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList") List<Long> idList, @Param("deletedFlag") boolean deletedFlag);

}
