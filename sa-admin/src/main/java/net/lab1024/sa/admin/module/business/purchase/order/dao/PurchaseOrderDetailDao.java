package net.lab1024.sa.admin.module.business.purchase.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.purchase.order.domain.entity.PurchaseOrderDetailEntity;
import net.lab1024.sa.admin.module.business.purchase.order.domain.form.PurchaseOrderDetailQueryForm;
import net.lab1024.sa.admin.module.business.purchase.order.domain.vo.PurchaseOrderDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 采购订货单详情 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-07-08 22:10:24
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface PurchaseOrderDetailDao extends BaseMapper<PurchaseOrderDetailEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<PurchaseOrderDetailVO> queryPage(Page page, @Param("queryForm") PurchaseOrderDetailQueryForm queryForm);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("purchaseOrderDetailId") Long purchaseOrderDetailId, @Param("deletedFlag") boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList") List<Long> idList, @Param("deletedFlag") boolean deletedFlag);

    List<PurchaseOrderDetailVO> selectQueryPage(Page<?> page, @Valid PurchaseOrderDetailQueryForm queryForm);
}
