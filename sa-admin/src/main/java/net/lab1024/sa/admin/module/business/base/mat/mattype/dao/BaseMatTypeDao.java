package net.lab1024.sa.admin.module.business.base.mat.mattype.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.base.mat.mattype.domain.entity.BaseMatTypeEntity;
import net.lab1024.sa.admin.module.business.base.mat.mattype.domain.form.BaseMatTypeQueryForm;
import net.lab1024.sa.admin.module.business.base.mat.mattype.domain.vo.BaseMatTypeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 物料分类表 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-04-01 22:25:10
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface BaseMatTypeDao extends BaseMapper<BaseMatTypeEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<BaseMatTypeVO> queryPage(Page page, @Param("queryForm") BaseMatTypeQueryForm queryForm);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("matTypeId")Long matTypeId,@Param("deletedFlag")boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList")List<Long> idList,@Param("deletedFlag")boolean deletedFlag);

    List<BaseMatTypeVO> selectByKeywords(String keywords);

    BaseMatTypeVO findTopParentById(@Param("matTypeId") Long matTypeId);
}
