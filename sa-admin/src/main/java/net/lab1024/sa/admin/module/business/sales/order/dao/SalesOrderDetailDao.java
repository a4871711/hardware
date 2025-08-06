package net.lab1024.sa.admin.module.business.sales.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.sales.order.domain.entity.SalesOrderDetailEntity;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderDetailQueryForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.vo.SalesOrderDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 销售订货单明细表 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-05-25 19:36:41
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface SalesOrderDetailDao extends BaseMapper<SalesOrderDetailEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<SalesOrderDetailVO> queryPage(Page page, @Param("queryForm") SalesOrderDetailQueryForm queryForm);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("salesOrderDetailId") Long salesOrderDetailId, @Param("deletedFlag") boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList") List<Long> idList, @Param("deletedFlag") boolean deletedFlag);

    List<SalesOrderDetailVO> findBySalesOrderId(Long salesOrderId);
}
