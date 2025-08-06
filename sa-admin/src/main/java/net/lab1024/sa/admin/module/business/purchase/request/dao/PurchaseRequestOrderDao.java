package net.lab1024.sa.admin.module.business.purchase.request.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.purchase.request.domain.entity.PurchaseRequestOrderEntity;
import net.lab1024.sa.admin.module.business.purchase.request.domain.form.PurchaseRequestOrderQueryForm;
import net.lab1024.sa.admin.module.business.purchase.request.domain.form.PurchaseRequestOrderReferenceForm;
import net.lab1024.sa.admin.module.business.purchase.request.domain.vo.PurchaseRequestOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 采购申请单 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-06-23 22:34:10
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface PurchaseRequestOrderDao extends BaseMapper<PurchaseRequestOrderEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<PurchaseRequestOrderVO> queryPage(Page page, @Param("queryForm") PurchaseRequestOrderQueryForm queryForm);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("purchaseRequestOrderId") Long purchaseRequestOrderId, @Param("deletedFlag") boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList") List<Long> idList, @Param("deletedFlag") boolean deletedFlag);

    List<PurchaseRequestOrderVO> isReferenced(@Param("form") PurchaseRequestOrderReferenceForm purchaseRequestOrderReferenceForm);
}
