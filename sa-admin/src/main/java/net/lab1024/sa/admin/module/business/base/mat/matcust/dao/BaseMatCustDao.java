package net.lab1024.sa.admin.module.business.base.mat.matcust.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.base.mat.matcust.domain.entity.BaseMatCustEntity;
import net.lab1024.sa.admin.module.business.base.mat.matcust.domain.form.BaseMatCustQueryForm;
import net.lab1024.sa.admin.module.business.base.mat.matcust.domain.vo.BaseMatCustVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 物料供应商单价表 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-04-22 00:40:08
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface BaseMatCustDao extends BaseMapper<BaseMatCustEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<BaseMatCustVO> queryPage(Page page, @Param("queryForm") BaseMatCustQueryForm queryForm);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("matCustId") Long matCustId, @Param("deletedFlag") boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList") List<Long> idList, @Param("deletedFlag") boolean deletedFlag);


    List<BaseMatCustVO> findListByMatId(@Param("matId") Long matId);

}
