package net.lab1024.sa.admin.module.business.purchase.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.purchase.order.domain.entity.PurchaseOrderEntity;
import net.lab1024.sa.admin.module.business.purchase.order.domain.form.PurchaseOrderQueryForm;
import net.lab1024.sa.admin.module.business.purchase.order.domain.vo.PurchaseOrderVO;
import net.lab1024.sa.base.common.domain.ValidateList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 采购订货单 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-07-08 22:10:23
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface PurchaseOrderDao extends BaseMapper<PurchaseOrderEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<PurchaseOrderVO> queryPage(Page page, @Param("queryForm") PurchaseOrderQueryForm queryForm);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("purchaseOrderId") Long purchaseOrderId, @Param("deletedFlag") boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList") List<Long> idList, @Param("deletedFlag") boolean deletedFlag);

    List<PurchaseOrderVO> isReferenced(ValidateList<Long> purchaseRequestOrderDetailIds);
}
