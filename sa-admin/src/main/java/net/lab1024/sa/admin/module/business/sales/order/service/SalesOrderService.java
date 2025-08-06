package net.lab1024.sa.admin.module.business.sales.order.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.base.mat.mattype.dao.BaseMatTypeDao;
import net.lab1024.sa.admin.module.business.sales.order.dao.SalesOrderDao;
import net.lab1024.sa.admin.module.business.sales.order.domain.entity.SalesOrderDetailEntity;
import net.lab1024.sa.admin.module.business.sales.order.domain.entity.SalesOrderEntity;
import net.lab1024.sa.admin.module.business.sales.order.domain.entity.SalesOrderMatEntity;
import net.lab1024.sa.admin.module.business.sales.order.domain.entity.SalesOrderRequirementsEntity;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderAddForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderQueryForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderUpdateForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.vo.*;
import net.lab1024.sa.admin.module.business.sales.order.manager.SalesOrderDetailManager;
import net.lab1024.sa.admin.module.business.sales.order.manager.SalesOrderManager;
import net.lab1024.sa.admin.module.business.sales.order.manager.SalesOrderMatManager;
import net.lab1024.sa.admin.module.business.sales.order.manager.SalesOrderRequirementsManager;
import net.lab1024.sa.admin.module.system.setting.dao.SettingDao;
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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 销售订货单 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-05-25 19:36:32
 * @Copyright 赵嘉伟
 */

@Service
public class SalesOrderService {

    @Resource
    private SalesOrderDao salesOrderDao;

    @Resource
    private SalesOrderManager salesOrderManager;

    @Resource
    private SalesOrderDetailManager salesOrderDetailManager;

    @Resource
    private SalesOrderRequirementsManager salesOrderRequirementsManager;

    @Resource
    private SalesOrderMatManager salesOrderMatManager;

    @Resource
    private SettingDao settingDao;

    @Resource
    private BaseMatTypeDao matTypeDao;

    @Resource
    private SerialNumberService serialNumberService;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<SalesOrderVO> queryPage(SalesOrderQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<SalesOrderVO> list = salesOrderDao.queryPage(page, queryForm);
        PageResult<SalesOrderVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    public PageResult<SalesOrderQuerySearchVO> querySearch(@Valid SalesOrderQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<SalesOrderQuerySearchVO> list = salesOrderDao.querySearch(page, queryForm);
        PageResult<SalesOrderQuerySearchVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(SalesOrderAddForm addForm) {
        SalesOrderEntity salesOrderEntity = SmartBeanUtil.copy(addForm, SalesOrderEntity.class);
        salesOrderDao.insert(salesOrderEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(SalesOrderUpdateForm updateForm) {
        SalesOrderEntity salesOrderEntity = SmartBeanUtil.copy(updateForm, SalesOrderEntity.class);
        salesOrderDao.updateById(salesOrderEntity);
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

        salesOrderDao.batchUpdateDeleted(idList, true);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long salesOrderId) {
        if (null == salesOrderId) {
            return ResponseDTO.ok();
        }

        salesOrderDao.updateDeleted(salesOrderId, true);
        return ResponseDTO.ok();
    }

    @Transactional(rollbackFor = Throwable.class)
    public ResponseDTO<Long> saveOrUpdate(@Valid SalesOrderAddForm addForm) {
        // 1. 生成销售单号
        if (StrUtil.isBlank(addForm.getOrderCode())) {
            addForm.setOrderCode(geneCodeRule(SmartRequestUtil.getRequestCompanyId()));
        }

        // 2. 保存主表
        SalesOrderEntity salesOrderEntity = SmartBeanUtil.copy(addForm, SalesOrderEntity.class);
        salesOrderManager.saveOrUpdate(salesOrderEntity);
        Long salesOrderId = salesOrderEntity.getSalesOrderId();

        // 3. 全量保存详情列表（先删后插）
        List<SalesOrderDetailEntity> detailEntityList =
                SmartBeanUtil.copyList(addForm.getSalesOrderDetailList(), SalesOrderDetailEntity.class);
        if (CollUtil.isNotEmpty(detailEntityList)) {
            for (SalesOrderDetailEntity entity : detailEntityList) {
                entity.setSalesOrderId(salesOrderId);
            }
            // 删除旧数据
            List<Long> collect = detailEntityList.stream().map(SalesOrderDetailEntity::getSalesOrderDetailId).filter(Objects::nonNull).collect(Collectors.toList());
            salesOrderDetailManager.remove(
                    Wrappers.<SalesOrderDetailEntity>lambdaQuery().eq(SalesOrderDetailEntity::getSalesOrderId, salesOrderId)
                            .notIn(CollUtil.isNotEmpty(collect), SalesOrderDetailEntity::getSalesOrderDetailId, collect)
            );
            // 插入新数据dfs
            salesOrderDetailManager.saveOrUpdateBatch(detailEntityList);
        } else {
            // 若传空，表示清空
            salesOrderDetailManager.remove(
                    Wrappers.<SalesOrderDetailEntity>lambdaQuery().eq(SalesOrderDetailEntity::getSalesOrderId, salesOrderId)
            );
        }

        // 4. 全量保存订单要求（先删后插）
        List<SalesOrderRequirementsEntity> requirementEntityList =
                SmartBeanUtil.copyList(addForm.getSalesOrderRequirementsList(), SalesOrderRequirementsEntity.class);
        if (CollUtil.isNotEmpty(requirementEntityList)) {
            for (SalesOrderRequirementsEntity entity : requirementEntityList) {
                entity.setSalesOrderId(salesOrderId);
            }
            List<Long> collect = requirementEntityList.stream().map(SalesOrderRequirementsEntity::getSalesOrderRequirementsId).filter(Objects::nonNull).collect(Collectors.toList());
            salesOrderRequirementsManager.remove(
                    Wrappers.<SalesOrderRequirementsEntity>lambdaQuery().eq(SalesOrderRequirementsEntity::getSalesOrderId, salesOrderId)
                            .notIn(CollUtil.isNotEmpty(collect), SalesOrderRequirementsEntity::getSalesOrderRequirementsId, collect)
            );
            salesOrderRequirementsManager.saveOrUpdateBatch(requirementEntityList);
        } else {
            salesOrderRequirementsManager.remove(
                    Wrappers.<SalesOrderRequirementsEntity>lambdaQuery().eq(SalesOrderRequirementsEntity::getSalesOrderId, salesOrderId)
            );
        }

        // 5. 全量保存原材料/加工件（先删后插）
        List<SalesOrderMatEntity> matEntityList =
                SmartBeanUtil.copyList(addForm.getSalesOrderMatAddForms(), SalesOrderMatEntity.class);
        if (CollUtil.isNotEmpty(matEntityList)) {
            for (SalesOrderMatEntity entity : matEntityList) {
                entity.setSalesOrderId(salesOrderId);
            }
            List<Long> collect = matEntityList.stream().map(SalesOrderMatEntity::getSalesOrderMatId).filter(Objects::nonNull).collect(Collectors.toList());
            salesOrderMatManager.remove(
                    Wrappers.<SalesOrderMatEntity>lambdaQuery().eq(SalesOrderMatEntity::getSalesOrderId, salesOrderId)
                            .notIn(CollUtil.isNotEmpty(collect), SalesOrderMatEntity::getSalesOrderMatId, collect)
            );
            salesOrderMatManager.saveOrUpdateBatch(matEntityList);
        } else {
            salesOrderMatManager.remove(
                    Wrappers.<SalesOrderMatEntity>lambdaQuery().eq(SalesOrderMatEntity::getSalesOrderId, salesOrderId)
            );
        }

        return ResponseDTO.ok(salesOrderId);
    }

    public String geneCodeRule(Long companyId) {
        return serialNumberService.generate(SerialNumberIdEnum.SALES_ORDER, companyId);
    }

    public SalesOrderDetailDataVO detail(Long salesOrderId) {
        SalesOrderDetailDataVO result = new SalesOrderDetailDataVO();


        // 获取salesOrder的数据
        SalesOrderVO salesOrderVO = salesOrderManager.getBaseMapper().findById(salesOrderId);
        result.setSalesOrder(salesOrderVO);


        // SalesOrderDetailVO
        List<SalesOrderDetailVO> salesOrderDetailVOList = salesOrderDetailManager.getBaseMapper()
                .findBySalesOrderId(salesOrderId);
        result.setSalesOrderDetailList(salesOrderDetailVOList);


        // 获取salesOrderRequirements的数据
        List<SalesOrderRequirementsVO> salesOrderRequirementsVOList = salesOrderRequirementsManager.findById(salesOrderId);
        result.setSalesOrderRequirementsList(salesOrderRequirementsVOList);

        // 获取salesOrderMat的数据
        List<SalesOrderMatVO> salesOrderMatVOList = salesOrderMatManager.getBaseMapper().findBySalesOrderId(salesOrderId);
        result.setSalesOrderMatList(salesOrderMatVOList);

        return result;
    }

    public ResponseDTO<String> audit(@Valid SalesOrderAddForm salesOrderAddForm) {
        LambdaUpdateWrapper<SalesOrderEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SalesOrderEntity::getSalesOrderId, salesOrderAddForm.getSalesOrderId())
                .set(SalesOrderEntity::getAudit, salesOrderAddForm.getAudit());
        salesOrderDao.update(null, updateWrapper);
        return ResponseDTO.ok();
    }

    public ResponseDTO<String> invalid(@Valid SalesOrderAddForm salesOrderAddForm) {
        LambdaUpdateWrapper<SalesOrderEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SalesOrderEntity::getSalesOrderId, salesOrderAddForm.getSalesOrderId())
                .set(SalesOrderEntity::getInvalid, salesOrderAddForm.getInvalid());
        salesOrderDao.update(null, updateWrapper);
        return ResponseDTO.ok();
    }


}
