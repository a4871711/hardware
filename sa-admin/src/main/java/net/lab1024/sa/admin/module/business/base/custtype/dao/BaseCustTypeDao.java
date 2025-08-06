package net.lab1024.sa.admin.module.business.base.custtype.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.base.custtype.domain.entity.BaseCustTypeEntity;
import net.lab1024.sa.admin.module.business.base.custtype.domain.form.BaseCustTypeQueryForm;
import net.lab1024.sa.admin.module.business.base.custtype.domain.vo.BaseCustTypeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 客商类型 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-03-28 21:27:26
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface BaseCustTypeDao extends BaseMapper<BaseCustTypeEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<BaseCustTypeVO> queryPage(Page page, @Param("queryForm") BaseCustTypeQueryForm queryForm);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("custTypeId") Long custTypeId, @Param("deletedFlag") boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList") List<Long> idList, @Param("deletedFlag") boolean deletedFlag);

    BaseCustTypeVO getDetailById(@Param("custTypeId") Long custTypeId);

    List<BaseCustTypeVO> selectByKeywords(String keywords);
}
