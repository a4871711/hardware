package net.lab1024.sa.admin.module.business.purchase.request.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.purchase.request.domain.entity.PurchaseRequestOrderDetailEntity;
import net.lab1024.sa.admin.module.business.purchase.request.domain.form.PurchaseRequestOrderDetailQueryForm;
import net.lab1024.sa.admin.module.business.purchase.request.domain.form.PurchaseRequestOrderQueryForm;
import net.lab1024.sa.admin.module.business.purchase.request.domain.vo.PurchaseRequestOrderDetailVO;
import net.lab1024.sa.admin.module.business.purchase.request.domain.vo.PurchaseRequestOrderDetailInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 采购申请单详情 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-06-23 22:34:08
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface PurchaseRequestOrderDetailDao extends BaseMapper<PurchaseRequestOrderDetailEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<PurchaseRequestOrderDetailVO> queryPage(Page page, @Param("queryForm") PurchaseRequestOrderDetailQueryForm queryForm);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("purchaseRequestOrderDetailId") Long purchaseRequestOrderDetailId, @Param("deletedFlag") boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList") List<Long> idList, @Param("deletedFlag") boolean deletedFlag);

    List<PurchaseRequestOrderDetailVO> findByPurchaseRequestOrderId(Long purchaseRequestOrderId);

    /**
     * 分页查询（已审核且未被已审核采购订货单引用的明细）
     */
    List<PurchaseRequestOrderDetailInfoVO> queryDetailPage(Page page, @Param("queryForm") PurchaseRequestOrderQueryForm queryForm);
}
