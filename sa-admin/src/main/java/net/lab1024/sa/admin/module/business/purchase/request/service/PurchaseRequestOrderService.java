package net.lab1024.sa.admin.module.business.purchase.request.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.purchase.request.dao.PurchaseRequestOrderDao;
import net.lab1024.sa.admin.module.business.purchase.request.domain.entity.PurchaseRequestOrderDetailEntity;
import net.lab1024.sa.admin.module.business.purchase.request.domain.entity.PurchaseRequestOrderEntity;
import net.lab1024.sa.admin.module.business.purchase.request.domain.form.*;
import net.lab1024.sa.admin.module.business.purchase.request.domain.vo.PurchaseRequestOrderDetailDataVO;
import net.lab1024.sa.admin.module.business.purchase.request.domain.vo.PurchaseRequestOrderDetailVO;
import net.lab1024.sa.admin.module.business.purchase.request.domain.vo.PurchaseRequestOrderVO;
import net.lab1024.sa.admin.module.business.purchase.request.manager.PurchaseRequestOrderDetailManager;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import net.lab1024.sa.base.common.util.SmartRequestUtil;
import net.lab1024.sa.base.module.support.serialnumber.constant.SerialNumberIdEnum;
import net.lab1024.sa.base.module.support.serialnumber.service.SerialNumberService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 采购申请单 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-06-23 22:34:10
 * @Copyright 赵嘉伟
 */

@Service
public class PurchaseRequestOrderService {

    @Resource
    private PurchaseRequestOrderDao purchaseRequestOrderDao;

    @Resource
    private SerialNumberService serialNumberService;

    @Resource
    private PurchaseRequestOrderDetailService purchaseRequestOrderDetailService;

    @Resource
    private PurchaseRequestOrderDetailManager purchaseRequestOrderDetailManager;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<PurchaseRequestOrderVO> queryPage(PurchaseRequestOrderQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<PurchaseRequestOrderVO> list = purchaseRequestOrderDao.queryPage(page, queryForm);
        PageResult<PurchaseRequestOrderVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(PurchaseRequestOrderAddForm addForm) {
        PurchaseRequestOrderEntity purchaseRequestOrderEntity = SmartBeanUtil.copy(addForm,
                PurchaseRequestOrderEntity.class);
        purchaseRequestOrderDao.insert(purchaseRequestOrderEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(PurchaseRequestOrderUpdateForm updateForm) {
        PurchaseRequestOrderEntity purchaseRequestOrderEntity = SmartBeanUtil.copy(updateForm,
                PurchaseRequestOrderEntity.class);
        purchaseRequestOrderDao.updateById(purchaseRequestOrderEntity);
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

        purchaseRequestOrderDao.batchUpdateDeleted(idList, true);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long purchaseRequestOrderId) {
        if (null == purchaseRequestOrderId) {
            return ResponseDTO.ok();
        }

        purchaseRequestOrderDao.updateDeleted(purchaseRequestOrderId, true);
        return ResponseDTO.ok();
    }

    @Transactional(rollbackFor = Throwable.class)
    public ResponseDTO<Long> saveOrUpdate(@Valid PurchaseRequestOrderAddForm addForm) {
        // 1. 生成采购单号
        if (StrUtil.isBlank(addForm.getPurchaseRequestOrderCode())) {
            addForm.setPurchaseRequestOrderCode(geneCodeRule(SmartRequestUtil.getRequestCompanyId()));
        }

        // 2. 保存主表
        PurchaseRequestOrderEntity purchaseRequestOrderEntity = SmartBeanUtil.copy(addForm,
                PurchaseRequestOrderEntity.class);
        purchaseRequestOrderDao.insertOrUpdate(purchaseRequestOrderEntity);
        Long purchaseRequestOrderId = purchaseRequestOrderEntity.getPurchaseRequestOrderId();

        // 3. 全量保存详情列表（先删后插）
        List<PurchaseRequestOrderDetailEntity> detailEntityList = SmartBeanUtil
                .copyList(addForm.getPurchaseRequestOrderDetailAddFormList(), PurchaseRequestOrderDetailEntity.class);
        if (CollUtil.isNotEmpty(detailEntityList)) {
            for (PurchaseRequestOrderDetailEntity entity : detailEntityList) {
                entity.setPurchaseRequestOrderId(purchaseRequestOrderId);
            }
            // 删除旧数据
            List<Long> collect = detailEntityList.stream()
                    .map(PurchaseRequestOrderDetailEntity::getPurchaseRequestOrderDetailId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            LambdaQueryWrapper<PurchaseRequestOrderDetailEntity> purchaseRequestOrderDetailEntityLambdaQueryWrapper = Wrappers
                    .<PurchaseRequestOrderDetailEntity>lambdaQuery()
                    .eq(PurchaseRequestOrderDetailEntity::getPurchaseRequestOrderId, purchaseRequestOrderId)
                    .notIn(CollUtil.isNotEmpty(collect),
                            PurchaseRequestOrderDetailEntity::getPurchaseRequestOrderDetailId, collect);
            purchaseRequestOrderDetailManager.remove(purchaseRequestOrderDetailEntityLambdaQueryWrapper);

            // 插入新数据
            purchaseRequestOrderDetailManager.saveOrUpdateBatch(detailEntityList);
        } else {
            // 若传空，表示清空
            purchaseRequestOrderDetailManager.remove(Wrappers.<PurchaseRequestOrderDetailEntity>lambdaQuery()
                    .eq(PurchaseRequestOrderDetailEntity::getPurchaseRequestOrderId, purchaseRequestOrderId));
        }

        return ResponseDTO.ok(purchaseRequestOrderId);
    }

    public String geneCodeRule(Long companyId) {
        return serialNumberService.generate(SerialNumberIdEnum.PURCHASE_REQUEST_ORDER, companyId);
    }

    public PurchaseRequestOrderDetailDataVO detail(Long purchaseRequestOrderId) {
        PurchaseRequestOrderDetailDataVO result = new PurchaseRequestOrderDetailDataVO();


        // purchaseRequestOrderEntity 的数据
        PurchaseRequestOrderEntity purchaseRequestOrderEntity = purchaseRequestOrderDao.selectById(purchaseRequestOrderId);
        PurchaseRequestOrderVO purchaseRequestOrderVO = SmartBeanUtil.copy(purchaseRequestOrderEntity, PurchaseRequestOrderVO.class);
        result.setPurchaseRequestOrder(purchaseRequestOrderVO);


        // SalesOrderDetailVO
        List<PurchaseRequestOrderDetailVO> purchaseRequestOrderDetailVOList = purchaseRequestOrderDetailManager.getBaseMapper().findByPurchaseRequestOrderId(purchaseRequestOrderId);
        result.setPurchaseRequestOrderDetailList(purchaseRequestOrderDetailVOList);
        return result;
    }

    public ResponseDTO<String> changedStatus(@Valid PurchaseRequestOrderChangedStatusForm purchaseRequestOrderChangedStatusForm) {
        LambdaUpdateWrapper<PurchaseRequestOrderDetailEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(PurchaseRequestOrderDetailEntity::getPurchaseRequestOrderDetailId, purchaseRequestOrderChangedStatusForm.getPurchaseRequestOrderDetailIdList())
                .set(PurchaseRequestOrderDetailEntity::getStatus, purchaseRequestOrderChangedStatusForm.getStatus());

        if (Objects.equals(purchaseRequestOrderChangedStatusForm.getStatus(), "1")) {
            updateWrapper.set(PurchaseRequestOrderDetailEntity::getAuditTime, LocalDateTime.now());
            updateWrapper.set(PurchaseRequestOrderDetailEntity::getAuditUserId, SmartRequestUtil.getRequestUserId());
            updateWrapper.set(PurchaseRequestOrderDetailEntity::getAuditUserName, SmartRequestUtil.getRequestUserName());
        } else {
            updateWrapper.set(PurchaseRequestOrderDetailEntity::getAuditTime, null);
            updateWrapper.set(PurchaseRequestOrderDetailEntity::getAuditUserId, null);
            updateWrapper.set(PurchaseRequestOrderDetailEntity::getAuditUserName, null);
        }

        purchaseRequestOrderDetailManager.getBaseMapper().update(null, updateWrapper);
        return ResponseDTO.ok();
    }

    public ResponseDTO<String> invalid(@Valid PurchaseRequestOrderAddForm purchaseRequestOrderAddForm) {
        LambdaUpdateWrapper<PurchaseRequestOrderEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PurchaseRequestOrderEntity::getPurchaseRequestOrderId, purchaseRequestOrderAddForm.getPurchaseRequestOrderId())
                .set(PurchaseRequestOrderEntity::getInvalid, purchaseRequestOrderAddForm.getInvalid());
        purchaseRequestOrderDao.update(null, updateWrapper);
        return ResponseDTO.ok();
    }

    public ResponseDTO<List<PurchaseRequestOrderVO>> isReferenced(@Valid PurchaseRequestOrderReferenceForm purchaseRequestOrderReferenceForm) {
        return ResponseDTO.ok(purchaseRequestOrderDao.isReferenced(purchaseRequestOrderReferenceForm));
    }
}
