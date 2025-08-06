package net.lab1024.sa.admin.module.business.purchase.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.purchase.order.dao.PurchaseOrderDetailDao;
import net.lab1024.sa.admin.module.business.purchase.order.domain.entity.PurchaseOrderDetailEntity;
import net.lab1024.sa.admin.module.business.purchase.order.domain.form.PurchaseOrderDetailAddForm;
import net.lab1024.sa.admin.module.business.purchase.order.domain.form.PurchaseOrderDetailQueryForm;
import net.lab1024.sa.admin.module.business.purchase.order.domain.form.PurchaseOrderDetailUpdateForm;
import net.lab1024.sa.admin.module.business.purchase.order.domain.vo.PurchaseOrderDetailVO;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 采购订货单详情 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-07-08 22:10:24
 * @Copyright 赵嘉伟
 */

@Service
public class PurchaseOrderDetailService {

    @Resource
    private PurchaseOrderDetailDao purchaseOrderDetailDao;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<PurchaseOrderDetailVO> queryPage(PurchaseOrderDetailQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<PurchaseOrderDetailVO> list = purchaseOrderDetailDao.queryPage(page, queryForm);
        PageResult<PurchaseOrderDetailVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    public PageResult<PurchaseOrderDetailVO> selectQueryPage(@Valid PurchaseOrderDetailQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<PurchaseOrderDetailVO> list = purchaseOrderDetailDao.selectQueryPage(page, queryForm);
        PageResult<PurchaseOrderDetailVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(PurchaseOrderDetailAddForm addForm) {
        PurchaseOrderDetailEntity purchaseOrderDetailEntity = SmartBeanUtil.copy(addForm, PurchaseOrderDetailEntity.class);
        purchaseOrderDetailDao.insert(purchaseOrderDetailEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(PurchaseOrderDetailUpdateForm updateForm) {
        PurchaseOrderDetailEntity purchaseOrderDetailEntity = SmartBeanUtil.copy(updateForm, PurchaseOrderDetailEntity.class);
        purchaseOrderDetailDao.updateById(purchaseOrderDetailEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     *
     * @param idList
     * @return
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return ResponseDTO.ok();
        }

        purchaseOrderDetailDao.batchUpdateDeleted(idList, true);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long purchaseOrderDetailId) {
        if (null == purchaseOrderDetailId) {
            return ResponseDTO.ok();
        }

        purchaseOrderDetailDao.updateDeleted(purchaseOrderDetailId, true);
        return ResponseDTO.ok();
    }


}
