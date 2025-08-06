package net.lab1024.sa.admin.module.business.sales.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.sales.order.domain.entity.SalesOrderEntity;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderQueryForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.vo.SalesOrderQuerySearchVO;
import net.lab1024.sa.admin.module.business.sales.order.domain.vo.SalesOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 销售订货单 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-05-25 19:36:32
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface SalesOrderDao extends BaseMapper<SalesOrderEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<SalesOrderVO> queryPage(Page page, @Param("queryForm") SalesOrderQueryForm queryForm);

    List<SalesOrderQuerySearchVO> querySearch(Page page, @Param("queryForm") SalesOrderQueryForm queryForm);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("salesOrderId") Long salesOrderId, @Param("deletedFlag") boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList") List<Long> idList, @Param("deletedFlag") boolean deletedFlag);

    SalesOrderVO findById(Long salesOrderId);


}
