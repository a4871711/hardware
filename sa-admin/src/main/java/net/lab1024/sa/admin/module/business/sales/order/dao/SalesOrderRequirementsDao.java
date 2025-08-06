package net.lab1024.sa.admin.module.business.sales.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.sales.order.domain.entity.SalesOrderRequirementsEntity;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderRequirementsQueryForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.vo.SalesOrderRequirementsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 销售订单-订单要求表 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-05-25 19:36:51
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface SalesOrderRequirementsDao extends BaseMapper<SalesOrderRequirementsEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<SalesOrderRequirementsVO> queryPage(Page page, @Param("queryForm") SalesOrderRequirementsQueryForm queryForm);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("salesOrderRequirementsId") Long salesOrderRequirementsId, @Param("deletedFlag") boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList") List<Long> idList, @Param("deletedFlag") boolean deletedFlag);

    List<SalesOrderRequirementsVO> findById(Long salesOrderId);
}
