package net.lab1024.sa.admin.module.business.base.cust.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.base.cust.domain.entity.BaseCustEntity;
import net.lab1024.sa.admin.module.business.base.cust.domain.form.BaseCustQueryForm;
import net.lab1024.sa.admin.module.business.base.cust.domain.vo.BaseCustDetailVO;
import net.lab1024.sa.admin.module.business.base.cust.domain.vo.BaseCustVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 客商资料 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-03-27 23:10:41
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface BaseCustDao extends BaseMapper<BaseCustEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<BaseCustVO> queryPage(Page page, @Param("queryForm") BaseCustQueryForm queryForm);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("baseCustId") Long baseCustId, @Param("deletedFlag") boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList") List<Long> idList, @Param("deletedFlag") boolean deletedFlag);

    BaseCustDetailVO getDetailById(@Param("custId") Long custId);

}
