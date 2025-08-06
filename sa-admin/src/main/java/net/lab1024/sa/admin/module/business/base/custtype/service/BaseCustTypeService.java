package net.lab1024.sa.admin.module.business.base.custtype.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.module.business.base.cust.domain.entity.BaseCustEntity;
import net.lab1024.sa.admin.module.business.base.cust.manager.BaseCustManager;
import net.lab1024.sa.admin.module.business.base.custtype.dao.BaseCustTypeDao;
import net.lab1024.sa.admin.module.business.base.custtype.domain.entity.BaseCustTypeEntity;
import net.lab1024.sa.admin.module.business.base.custtype.domain.form.BaseCustTypeAddForm;
import net.lab1024.sa.admin.module.business.base.custtype.domain.form.BaseCustTypeQueryForm;
import net.lab1024.sa.admin.module.business.base.custtype.domain.form.BaseCustTypeUpdateForm;
import net.lab1024.sa.admin.module.business.base.custtype.domain.vo.BaseCustTypeVO;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 客商类型 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-03-28 21:27:26
 * @Copyright 赵嘉伟
 */

@Service
public class BaseCustTypeService {

    @Resource
    private BaseCustTypeDao baseCustTypeDao;

    @Resource
    private BaseCustManager baseCustManager;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<BaseCustTypeVO> queryPage(BaseCustTypeQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<BaseCustTypeVO> list = baseCustTypeDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 添加
     */
    @Transactional(rollbackFor = Throwable.class)
    public ResponseDTO<String> add(BaseCustTypeAddForm addForm) {
        BaseCustTypeEntity baseCustTypeEntity = SmartBeanUtil.copy(addForm, BaseCustTypeEntity.class);
        //判断分类编码是否已经存在
        LambdaQueryWrapper<BaseCustTypeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseCustTypeEntity::getCustTypeCode, addForm.getCustTypeCode()).or().
        eq(BaseCustTypeEntity::getCustTypeName, addForm.getCustTypeName());
        if (baseCustTypeDao.selectOne(queryWrapper) != null) {
            return ResponseDTO.userErrorParam("客商分类编码或者名称已经存在");
        }
        baseCustTypeDao.insert(baseCustTypeEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(BaseCustTypeUpdateForm updateForm) {
        BaseCustTypeEntity baseCustTypeEntity = SmartBeanUtil.copy(updateForm, BaseCustTypeEntity.class);
        // 判断分类编码是否已经存在，排除当前记录的 id
        LambdaQueryWrapper<BaseCustTypeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(BaseCustTypeEntity::getCustTypeId, updateForm.getCustTypeId())
                .and(wrapper -> wrapper.eq(BaseCustTypeEntity::getCustTypeCode, updateForm.getCustTypeCode())
                        .or().eq(BaseCustTypeEntity::getCustTypeName, updateForm.getCustTypeName()));
        if (baseCustTypeDao.selectOne(queryWrapper) != null) {
            return ResponseDTO.userErrorParam("客商分类编码或者名称已经存在");
        }
        baseCustTypeDao.updateById(baseCustTypeEntity);
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

        baseCustTypeDao.batchUpdateDeleted(idList, true);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long custTypeId) {
        if (null == custTypeId) {
            return ResponseDTO.ok();
        }
        LambdaQueryWrapper<BaseCustTypeEntity> parentId = new LambdaQueryWrapper<BaseCustTypeEntity>()
                .eq(BaseCustTypeEntity::getParentId, custTypeId);
        // 判断该客商分类下是否有客商存在及其子分类存在
        if (baseCustTypeDao.selectCount(parentId) > 0) {
            return ResponseDTO.userErrorParam("该客商分类下存在子分类，请先删除子分类");
        }
        List<BaseCustEntity> custByCustTypeId = baseCustManager.getCustByCustTypeId(custTypeId);
        if (CollUtil.isNotEmpty(custByCustTypeId)) {
            return ResponseDTO.userErrorParam("该客商分类下存在客商，请先删除客商");
        }
        baseCustTypeDao.updateDeleted(custTypeId, true);
        return ResponseDTO.ok();
    }


    /**
     * 根据 custTypeId 查询客商分类详情
     */
    public ResponseDTO<BaseCustTypeVO> getDetailById(Long custTypeId) {
        if (custTypeId == null) {
            return ResponseDTO.userErrorParam("custTypeId不能为空");
        }
        BaseCustTypeVO baseCustTypeVO = baseCustTypeDao.getDetailById(custTypeId);
        if (baseCustTypeVO == null) {
            return ResponseDTO.userErrorParam("客商分类不存在");
        }
        return ResponseDTO.ok(baseCustTypeVO);
    }

    public ResponseDTO<List<BaseCustTypeVO>> queryByKeywords(String keywords) {
        // 实现根据关键词查询客商分类的逻辑
        List<BaseCustTypeVO> result = baseCustTypeDao.selectByKeywords(keywords);
        return ResponseDTO.ok(result);
    }


}
