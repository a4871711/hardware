package net.lab1024.sa.admin.module.business.purchase.order.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.purchase.order.dao.PurchaseOrderDao;
import net.lab1024.sa.admin.module.business.purchase.order.domain.entity.PurchaseOrderDetailEntity;
import net.lab1024.sa.admin.module.business.purchase.order.domain.entity.PurchaseOrderEntity;
import net.lab1024.sa.admin.module.business.purchase.order.domain.form.*;
import net.lab1024.sa.admin.module.business.purchase.order.domain.vo.PurchaseOrderVO;
import net.lab1024.sa.admin.module.business.purchase.order.manager.PurchaseOrderDetailManager;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.ValidateList;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import net.lab1024.sa.base.common.util.SmartRequestUtil;
import net.lab1024.sa.base.module.support.serialnumber.constant.SerialNumberIdEnum;
import net.lab1024.sa.base.module.support.serialnumber.service.SerialNumberService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 采购订货单 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-07-08 22:10:23
 * @Copyright 赵嘉伟
 */

@Service
public class PurchaseOrderService {

    @Resource
    private PurchaseOrderDao purchaseOrderDao;

    @Resource
    private SerialNumberService serialNumberService;

    @Resource
    private PurchaseOrderDetailManager purchaseOrderDetailManager;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<PurchaseOrderVO> queryPage(PurchaseOrderQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<PurchaseOrderVO> list = purchaseOrderDao.queryPage(page, queryForm);
        PageResult<PurchaseOrderVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(PurchaseOrderAddForm addForm) {
        PurchaseOrderEntity purchaseOrderEntity = SmartBeanUtil.copy(addForm, PurchaseOrderEntity.class);
        purchaseOrderDao.insert(purchaseOrderEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(PurchaseOrderUpdateForm updateForm) {
        PurchaseOrderEntity purchaseOrderEntity = SmartBeanUtil.copy(updateForm, PurchaseOrderEntity.class);
        purchaseOrderDao.updateById(purchaseOrderEntity);
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

        purchaseOrderDao.batchUpdateDeleted(idList, true);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long purchaseOrderId) {
        if (null == purchaseOrderId) {
            return ResponseDTO.ok();
        }

        purchaseOrderDao.updateDeleted(purchaseOrderId, true);
        return ResponseDTO.ok();
    }

    public ResponseDTO<List<PurchaseOrderVO>> isReferenced(ValidateList<Long> idList) {
        return ResponseDTO.ok(purchaseOrderDao.isReferenced(idList));
    }

    public ResponseDTO<Long> saveOrUpdate(@Valid PurchaseOrderAddForm addForm) {
        // 1. 生成采购单号
        if (StrUtil.isBlank(addForm.getPurchaseOrderCode())) {
            addForm.setPurchaseOrderCode(geneCodeRule(SmartRequestUtil.getRequestCompanyId()));
        }

        // 2. 保存主表
        PurchaseOrderEntity purchaseOrderEntity = SmartBeanUtil.copy(addForm,
                PurchaseOrderEntity.class);
        purchaseOrderDao.insertOrUpdate(purchaseOrderEntity);
        Long purchaseOrderId = purchaseOrderEntity.getPurchaseOrderId();

        // 3. 全量保存详情列表（先删后插）
        List<PurchaseOrderDetailEntity> detailEntityList = SmartBeanUtil
                .copyList(addForm.getPurchaseOrderDetailAddFormList(), PurchaseOrderDetailEntity.class);
        if (CollUtil.isNotEmpty(detailEntityList)) {
            for (PurchaseOrderDetailEntity entity : detailEntityList) {
                entity.setPurchaseOrderId(purchaseOrderId);
            }
            // 删除旧数据
            List<Long> collect = detailEntityList.stream()
                    .map(PurchaseOrderDetailEntity::getPurchaseOrderDetailId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            LambdaQueryWrapper<PurchaseOrderDetailEntity> purchaseOrderDetailEntityLambdaQueryWrapper = Wrappers
                    .<PurchaseOrderDetailEntity>lambdaQuery()
                    .eq(PurchaseOrderDetailEntity::getPurchaseOrderId, purchaseOrderId)
                    .notIn(CollUtil.isNotEmpty(collect),
                            PurchaseOrderDetailEntity::getPurchaseRequestOrderDetailId, collect);
            purchaseOrderDetailManager.remove(purchaseOrderDetailEntityLambdaQueryWrapper);

            // 插入新数据
            purchaseOrderDetailManager.saveOrUpdateBatch(detailEntityList);
        } else {
            // 若传空，表示清空
            purchaseOrderDetailManager.remove(Wrappers.<PurchaseOrderDetailEntity>lambdaQuery()
                    .eq(PurchaseOrderDetailEntity::getPurchaseOrderId, purchaseOrderId));
        }
        return ResponseDTO.ok(purchaseOrderId);
    }

    public String geneCodeRule(Long companyId) {
        return serialNumberService.generate(SerialNumberIdEnum.PURCHASE_ORDER, companyId);
    }

    public ResponseDTO<String> audit(PurchaseOrderAuditForm purchaseOrderAuditForm) {
        LambdaUpdateWrapper<PurchaseOrderEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PurchaseOrderEntity::getPurchaseOrderId, purchaseOrderAuditForm.getPurchaseOrderId())
                .set(PurchaseOrderEntity::getAudit, purchaseOrderAuditForm.getAudit());
        purchaseOrderDao.update(null, updateWrapper);
        return ResponseDTO.ok();
    }

    public ResponseDTO<String> invalid(@Valid PurchaseOrderInvalidForm purchaseOrderInvalidForm) {
        LambdaUpdateWrapper<PurchaseOrderEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PurchaseOrderEntity::getPurchaseOrderId, purchaseOrderInvalidForm.getPurchaseOrderId())
                .set(PurchaseOrderEntity::getInvalid, purchaseOrderInvalidForm.getInvalid());
        purchaseOrderDao.update(null, updateWrapper);
        return ResponseDTO.ok();
    }
}
