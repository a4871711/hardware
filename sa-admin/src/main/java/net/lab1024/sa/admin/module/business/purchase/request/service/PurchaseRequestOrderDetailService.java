package net.lab1024.sa.admin.module.business.purchase.request.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.module.business.purchase.request.dao.PurchaseRequestOrderDetailDao;
import net.lab1024.sa.admin.module.business.purchase.request.domain.entity.PurchaseRequestOrderDetailEntity;
import net.lab1024.sa.admin.module.business.purchase.request.domain.form.PurchaseRequestOrderDetailAddForm;
import net.lab1024.sa.admin.module.business.purchase.request.domain.form.PurchaseRequestOrderDetailQueryForm;
import net.lab1024.sa.admin.module.business.purchase.request.domain.form.PurchaseRequestOrderDetailUpdateForm;
import net.lab1024.sa.admin.module.business.purchase.request.domain.form.PurchaseRequestOrderQueryForm;
import net.lab1024.sa.admin.module.business.purchase.request.domain.vo.PurchaseRequestOrderDetailVO;
import net.lab1024.sa.admin.module.business.purchase.request.domain.vo.PurchaseRequestOrderDetailInfoVO;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 采购申请单详情 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-06-23 22:34:08
 * @Copyright 赵嘉伟
 */

@Service
public class PurchaseRequestOrderDetailService {

    @Resource
    private PurchaseRequestOrderDetailDao purchaseRequestOrderDetailDao;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<PurchaseRequestOrderDetailVO> queryPage(PurchaseRequestOrderDetailQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<PurchaseRequestOrderDetailVO> list = purchaseRequestOrderDetailDao.queryPage(page, queryForm);
        PageResult<PurchaseRequestOrderDetailVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 分页查询 采购申请单明细
     */
    public PageResult<PurchaseRequestOrderDetailInfoVO> queryDetailPage(PurchaseRequestOrderQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<PurchaseRequestOrderDetailInfoVO> list = purchaseRequestOrderDetailDao.queryDetailPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(PurchaseRequestOrderDetailAddForm addForm) {
        PurchaseRequestOrderDetailEntity purchaseRequestOrderDetailEntity = SmartBeanUtil.copy(addForm, PurchaseRequestOrderDetailEntity.class);
        purchaseRequestOrderDetailDao.insert(purchaseRequestOrderDetailEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(PurchaseRequestOrderDetailUpdateForm updateForm) {
        PurchaseRequestOrderDetailEntity purchaseRequestOrderDetailEntity = SmartBeanUtil.copy(updateForm, PurchaseRequestOrderDetailEntity.class);
        purchaseRequestOrderDetailDao.updateById(purchaseRequestOrderDetailEntity);
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

        purchaseRequestOrderDetailDao.batchUpdateDeleted(idList, true);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long purchaseRequestOrderDetailId) {
        if (null == purchaseRequestOrderDetailId) {
            return ResponseDTO.ok();
        }

        purchaseRequestOrderDetailDao.updateDeleted(purchaseRequestOrderDetailId, true);
        return ResponseDTO.ok();
    }
}
