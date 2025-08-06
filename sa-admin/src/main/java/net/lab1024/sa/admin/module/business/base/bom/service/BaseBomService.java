package net.lab1024.sa.admin.module.business.base.bom.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.base.bom.dao.BaseBomDao;
import net.lab1024.sa.admin.module.business.base.bom.domain.entity.BaseBomEntity;
import net.lab1024.sa.admin.module.business.base.bom.domain.entity.BaseBomOtherAmountEntity;
import net.lab1024.sa.admin.module.business.base.bom.domain.entity.BaseBomProductEntity;
import net.lab1024.sa.admin.module.business.base.bom.domain.form.*;
import net.lab1024.sa.admin.module.business.base.bom.domain.vo.BaseBomDetailVO;
import net.lab1024.sa.admin.module.business.base.bom.domain.vo.BaseBomOtherAmountVO;
import net.lab1024.sa.admin.module.business.base.bom.domain.vo.BaseBomProductVO;
import net.lab1024.sa.admin.module.business.base.bom.domain.vo.BaseBomVO;
import net.lab1024.sa.admin.module.business.base.bom.manager.BaseBomManager;
import net.lab1024.sa.admin.module.business.base.bom.manager.BaseBomOtherAmountManager;
import net.lab1024.sa.admin.module.business.base.bom.manager.BaseBomProductManager;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 多级BOM-主表 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-04-25 23:33:04
 * @Copyright 赵嘉伟
 */

@Service
public class BaseBomService {

    @Resource
    private BaseBomDao baseBomDao;

    @Resource
    private BaseBomManager baseBomManager;

    @Resource
    private BaseBomProductManager baseBomProductManager;

    @Resource
    private BaseBomOtherAmountManager baseBomOtherAmountManager;


    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<BaseBomVO> queryPage(BaseBomQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<BaseBomVO> list = baseBomDao.queryPage(page, queryForm);
        PageResult<BaseBomVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    public PageResult<BaseBomVO> querySearch(BaseBomQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);

        List<BaseBomVO> list = baseBomDao.querySearch(page, queryForm);
        PageResult<BaseBomVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 添加
     */
    @Transactional(rollbackFor = Throwable.class)
    public ResponseDTO<String> add(BaseBomAddForm addForm) {
        //1. 批量插入产品材料
        List<BaseBomProductAddForm> bomProductList = addForm.getBomProductList();
        List<BaseBomProductEntity> baseBomProductEntities = SmartBeanUtil.copyList(bomProductList, BaseBomProductEntity.class);
        baseBomProductManager.saveBatch(baseBomProductEntities);

        //2. 插入其他费用
        List<BaseBomOtherAmountAddForm> bomOtherAmountList = addForm.getBomOtherAmountList();
        List<BaseBomOtherAmountEntity> baseBomOtherAmountEntities = SmartBeanUtil.copyList(bomOtherAmountList, BaseBomOtherAmountEntity.class);
        baseBomOtherAmountManager.saveBatch(baseBomOtherAmountEntities);

        //3. 插入主表
        BaseBomEntity baseBomEntity = SmartBeanUtil.copy(addForm, BaseBomEntity.class);
        baseBomDao.insert(baseBomEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    @Transactional(rollbackFor = Throwable.class)
    public ResponseDTO<Long> saveOrUpdate(BaseBomUpdateForm updateForm) {

        //更新主表
        BaseBomEntity baseBomEntity = SmartBeanUtil.copy(updateForm, BaseBomEntity.class);
        baseBomManager.saveOrUpdate(baseBomEntity);

        //删除bomProductId != baseBomProductEntities.getBomProductId()的数据，并且bomId = baseBomEntity.getBomId()
        LambdaUpdateWrapper<BaseBomProductEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(BaseBomProductEntity::getBomId, baseBomEntity.getBomId());
        if (CollectionUtil.isNotEmpty(updateForm.getBomProductList())) {
            //1. 批量更新产品材料
            List<BaseBomProductUpdateForm> flatList = new ArrayList<>();
            // 这块是一个树形结构，能否弄成list集合保存了，里面有一个children，然后id有可能为空
            fillAndFlattenBomProductTree(updateForm.getBomProductList(), 0L, flatList);
            List<BaseBomProductEntity> baseBomProductEntities = SmartBeanUtil.copyList(flatList, BaseBomProductEntity.class);
            for (BaseBomProductEntity baseBomProductEntity : baseBomProductEntities) {
                baseBomProductEntity.setBomId(baseBomEntity.getBomId());
            }
            updateWrapper.notIn(BaseBomProductEntity::getBomProductId, baseBomProductEntities.stream().map(BaseBomProductEntity::getBomProductId).toList());
            baseBomProductManager.saveOrUpdateBatch(baseBomProductEntities);
        }
        baseBomProductManager.getBaseMapper().delete(updateWrapper);

        //2. 批量更新其他费用
        List<BaseBomOtherAmountUpdateForm> bomOtherAmountList = updateForm.getBomOtherAmountList();
        for (BaseBomOtherAmountUpdateForm baseBomOtherAmountUpdateForm : bomOtherAmountList) {
            baseBomOtherAmountUpdateForm.setBomId(baseBomEntity.getBomId());
        }
        //删除bomProductId != baseBomProductEntities.getBomProductId()的数据，并且bomId = baseBomEntity.getBomId()
        LambdaUpdateWrapper<BaseBomOtherAmountEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(BaseBomOtherAmountEntity::getBomId, baseBomEntity.getBomId());
        if (CollectionUtil.isNotEmpty(updateForm.getBomOtherAmountList())) {
            wrapper.notIn(BaseBomOtherAmountEntity::getBomOtherAmountId, bomOtherAmountList.stream().map(BaseBomOtherAmountEntity::getBomOtherAmountId).toList());
            List<BaseBomOtherAmountEntity> baseBomOtherAmountEntities = SmartBeanUtil.copyList(bomOtherAmountList, BaseBomOtherAmountEntity.class);
            baseBomOtherAmountManager.saveOrUpdateBatch(baseBomOtherAmountEntities);
        }
        baseBomOtherAmountManager.getBaseMapper().delete(wrapper);

        return ResponseDTO.ok(baseBomEntity.getBomId());
    }

    public void fillAndFlattenBomProductTree(List<BaseBomProductUpdateForm> treeList, Long parentId, List<BaseBomProductUpdateForm> flatList) {
        if (treeList == null) return;
        for (BaseBomProductUpdateForm node : treeList) {
            // 生成 bomProductId
            if (node.getBomProductId() == null) {
                node.setBomProductId(IdUtil.getSnowflakeNextId());
            }
            // 设置 parentMatId
            node.setParentMatId(parentId);

            flatList.add(node);

            // 递归处理 children
            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                fillAndFlattenBomProductTree(node.getChildren(), node.getBomProductId(), flatList);
            }
        }
    }

    /**
     * 单个删除
     */
    @Transactional(rollbackFor = Throwable.class)
    public ResponseDTO<String> delete(Long bomId) {
        if (null == bomId) {
            return ResponseDTO.ok();
        }
        baseBomDao.updateDeleted(bomId, true);
        // 根据BOMID删除产品材料
        baseBomProductManager.deleteByBomId(bomId);
        // 根据BOMID删除其他费用
        baseBomOtherAmountManager.deleteByBomId(bomId);
        return ResponseDTO.ok();
    }

    public ResponseDTO<String> audit(BaseBomAuditForm baseBomAuditForm) {
        LambdaUpdateWrapper<BaseBomEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(BaseBomEntity::getBomId, baseBomAuditForm.getBomId())
                .set(BaseBomEntity::getAudit, baseBomAuditForm.getAudit());
        baseBomDao.update(null, updateWrapper);
        return ResponseDTO.ok();
    }

    public ResponseDTO<String> invalid(@Valid BaseBomInvalidForm baseBomInvalidForm) {
        LambdaUpdateWrapper<BaseBomEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(BaseBomEntity::getBomId, baseBomInvalidForm.getBomId())
                .set(BaseBomEntity::getInvalid, baseBomInvalidForm.getInvalid());
        baseBomDao.update(null, updateWrapper);
        return ResponseDTO.ok();
    }

    public ResponseDTO<BaseBomDetailVO> detail(Long bomId) {
        BaseBomDetailVO result = new BaseBomDetailVO();
        // 获取BOM详情
        BaseBomVO baseBomVO = baseBomDao.findById(bomId);
        result.setBaseBom(baseBomVO);

        //获取产品材料
        List<BaseBomProductVO> bomProductList = baseBomProductManager.getBaseMapper().findByBomId(bomId);
        result.setBomProductList(bomProductList);

        List<BaseBomOtherAmountVO> bomOtherAmountVOList = baseBomOtherAmountManager.getBaseMapper().findByBomId(bomId);

        result.setBomOtherAmountList(bomOtherAmountVOList);

        return ResponseDTO.ok(result);
    }

    public List<BaseBomProductVO> buildBomTree(List<BaseBomProductVO> bomProductList) {
        // 构建以 bomProductId 为 key 的映射，并初始化 children
        Map<Long, BaseBomProductVO> idMap = bomProductList.stream()
                .peek(item -> item.setChildren(new ArrayList<>()))
                .collect(Collectors.toMap(BaseBomProductVO::getBomProductId, Function.identity()));

        // 构建树结构
        List<BaseBomProductVO> rootList = new ArrayList<>();
        for (BaseBomProductVO item : bomProductList) {
            BaseBomProductVO parent = idMap.get(item.getParentMatId());
            if (parent != null) {
                parent.getChildren().add(item);
            } else {
                rootList.add(item);
            }
        }
        return rootList;
    }

    public ResponseDTO<List<BaseBomProductVO>> getByBomIdList(List<Long> bomIdList) {
        return ResponseDTO.ok(baseBomProductManager.getBaseMapper().findByBomIdList(bomIdList));
    }
}
