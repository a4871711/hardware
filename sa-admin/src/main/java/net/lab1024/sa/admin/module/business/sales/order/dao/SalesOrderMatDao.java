package net.lab1024.sa.admin.module.business.sales.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.sales.order.domain.entity.SalesOrderMatEntity;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderMatQueryForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.vo.SalesOrderMatVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 销售订单原材料表 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-05-29 23:32:37
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface SalesOrderMatDao extends BaseMapper<SalesOrderMatEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<SalesOrderMatVO> queryPage(Page page, @Param("queryForm") SalesOrderMatQueryForm queryForm);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("salesOrderMatId") Long salesOrderMatId, @Param("deletedFlag") boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList") List<Long> idList, @Param("deletedFlag") boolean deletedFlag);


    List<SalesOrderMatVO> findBySalesOrderId(@Param("salesOrderId") Long salesOrderId);
}
