package net.lab1024.sa.admin.module.business.base.mat.mattype.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.module.business.base.mat.mat.domain.entity.BaseMatEntity;
import net.lab1024.sa.admin.module.business.base.mat.mat.manager.BaseMatManager;
import net.lab1024.sa.admin.module.business.base.mat.mattype.dao.BaseMatTypeDao;
import net.lab1024.sa.admin.module.business.base.mat.mattype.domain.entity.BaseMatTypeEntity;
import net.lab1024.sa.admin.module.business.base.mat.mattype.domain.form.BaseMatTypeAddForm;
import net.lab1024.sa.admin.module.business.base.mat.mattype.domain.form.BaseMatTypeQueryForm;
import net.lab1024.sa.admin.module.business.base.mat.mattype.domain.form.BaseMatTypeUpdateForm;
import net.lab1024.sa.admin.module.business.base.mat.mattype.domain.vo.BaseMatTypeVO;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物料分类表 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-04-01 22:25:10
 * @Copyright 赵嘉伟
 */

@Service
public class BaseMatTypeService {

    @Resource
    private BaseMatTypeDao baseMatTypeDao;

    @Resource
    private BaseMatManager baseMatManager;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<BaseMatTypeVO> queryPage(BaseMatTypeQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<BaseMatTypeVO> list = baseMatTypeDao.queryPage(page, queryForm);
        PageResult<BaseMatTypeVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(BaseMatTypeAddForm addForm) {
        BaseMatTypeEntity baseMatTypeEntity = SmartBeanUtil.copy(addForm, BaseMatTypeEntity.class);
        //判断分类编码是否已经存在
        LambdaQueryWrapper<BaseMatTypeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseMatTypeEntity::getMatTypeCode, addForm.getMatTypeCode()).or()
                .eq(BaseMatTypeEntity::getMatTypeName, addForm.getMatTypeName());
        if (baseMatTypeDao.selectOne(queryWrapper) != null) {
            return ResponseDTO.userErrorParam("物料分类编码或者名称已经存在");
        }
        baseMatTypeDao.insert(baseMatTypeEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(BaseMatTypeUpdateForm updateForm) {
        BaseMatTypeEntity baseMatTypeEntity = SmartBeanUtil.copy(updateForm, BaseMatTypeEntity.class);
        // 判断分类编码是否已经存在，排除当前记录的 id
        LambdaQueryWrapper<BaseMatTypeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(BaseMatTypeEntity::getMatTypeId, updateForm.getMatTypeId())
                .and(wrapper -> wrapper.eq(BaseMatTypeEntity::getMatTypeCode, updateForm.getMatTypeCode())
                        .or().eq(BaseMatTypeEntity::getMatTypeName, updateForm.getMatTypeName()));
        if (baseMatTypeDao.selectOne(queryWrapper) != null) {
            return ResponseDTO.userErrorParam("物料分类编码或者名称已经存在");
        }
        baseMatTypeDao.updateById(baseMatTypeEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     *
     * @param idList
     * @return
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }

        baseMatTypeDao.batchUpdateDeleted(idList, true);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long matTypeId) {
        if (null == matTypeId){
            return ResponseDTO.ok();
        }
        LambdaQueryWrapper<BaseMatTypeEntity> parentId = new LambdaQueryWrapper<BaseMatTypeEntity>()
                .eq(BaseMatTypeEntity::getMatTypeParentId, matTypeId);
        if (baseMatTypeDao.selectCount(parentId) > 0) {
            return ResponseDTO.userErrorParam("该物料分类下存在子分类，请先删除子分类");
        }
        List<BaseMatEntity> matByMatTypeIdList = baseMatManager.getMatByMatTypeId(matTypeId);
        if (CollUtil.isNotEmpty(matByMatTypeIdList)) {
            return ResponseDTO.userErrorParam("该物料分类下存在物料，请先删除物料");
        }
        baseMatTypeDao.updateDeleted(matTypeId, true);
        return ResponseDTO.ok();
    }

    public ResponseDTO<List<BaseMatTypeVO>> queryByKeywords(String keywords) {
        // 实现根据关键词查询客商分类的逻辑
        List<BaseMatTypeVO> result = baseMatTypeDao.selectByKeywords(keywords);
        return ResponseDTO.ok(result);
    }
}
