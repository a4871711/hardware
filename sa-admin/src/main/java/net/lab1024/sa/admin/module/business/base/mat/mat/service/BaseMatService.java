package net.lab1024.sa.admin.module.business.base.mat.mat.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.base.mat.mat.dao.BaseMatDao;
import net.lab1024.sa.admin.module.business.base.mat.mat.domain.entity.BaseMatEntity;
import net.lab1024.sa.admin.module.business.base.mat.mat.domain.form.BaseMatAddForm;
import net.lab1024.sa.admin.module.business.base.mat.mat.domain.form.BaseMatQueryForm;
import net.lab1024.sa.admin.module.business.base.mat.mat.domain.form.BaseMatUpdateForm;
import net.lab1024.sa.admin.module.business.base.mat.mat.domain.form.InvalidForm;
import net.lab1024.sa.admin.module.business.base.mat.mat.domain.vo.BaseMatDetailVO;
import net.lab1024.sa.admin.module.business.base.mat.mat.domain.vo.BaseMatVO;
import net.lab1024.sa.admin.module.business.base.mat.matcust.domain.entity.BaseMatCustEntity;
import net.lab1024.sa.admin.module.business.base.mat.matcust.domain.form.BaseMatCustAddForm;
import net.lab1024.sa.admin.module.business.base.mat.matcust.domain.vo.BaseMatCustVO;
import net.lab1024.sa.admin.module.business.base.mat.matcust.manager.BaseMatCustManager;
import net.lab1024.sa.admin.module.business.base.mat.mattype.dao.BaseMatTypeDao;
import net.lab1024.sa.admin.module.business.base.mat.mattype.domain.entity.BaseMatTypeEntity;
import net.lab1024.sa.admin.module.business.base.mat.mattype.domain.vo.BaseMatTypeVO;
import net.lab1024.sa.admin.module.system.setting.dao.SettingDao;
import net.lab1024.sa.admin.module.system.setting.domain.vo.SettingVO;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import net.lab1024.sa.base.common.util.SmartRequestUtil;
import net.lab1024.sa.base.module.support.serialnumber.constant.SerialNumberIdEnum;
import net.lab1024.sa.base.module.support.serialnumber.service.SerialNumberService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 物料资料表 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-04-01 22:25:04
 * @Copyright 赵嘉伟
 */

@Service
public class BaseMatService {

    @Resource
    private BaseMatDao baseMatDao;

    @Resource
    private SettingDao settingDao;

    @Resource
    private BaseMatTypeDao matTypeDao;

    @Resource
    private SerialNumberService serialNumberService;

    @Resource
    private BaseMatCustManager baseMatCustManager;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<BaseMatVO> queryPage(BaseMatQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<BaseMatVO> list = baseMatDao.queryPage(page, queryForm);
        PageResult<BaseMatVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(BaseMatAddForm addForm) {
        //判断物料编码是否已经存在
        if (StrUtil.isBlank(addForm.getMatNo())) {
            String matNo = geneCodeRule(addForm.getMatTypeId(), SmartRequestUtil.getRequestCompanyId());
            addForm.setMatNo(matNo);
        }
        BaseMatEntity baseMatEntity = SmartBeanUtil.copy(addForm, BaseMatEntity.class);
        // 判断客商编码是否已经存在
        LambdaQueryWrapper<BaseMatEntity> baseMatEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        baseMatEntityLambdaQueryWrapper.eq(BaseMatEntity::getMatNo, addForm.getMatNo()).or()
                .eq(BaseMatEntity::getMatName, addForm.getMatName());
        if (baseMatDao.selectOne(baseMatEntityLambdaQueryWrapper) != null) {
            return ResponseDTO.userErrorParam("物料编码或者名称已经存在");
        }

        List<BaseMatCustAddForm> baseMatCustList = addForm.getBaseMatCustList();
        if (!CollectionUtils.isEmpty(baseMatCustList)) {
            // 1. 校验是否有重复的供应商
            Set<Long> custIdSet = new HashSet<>();
            for (BaseMatCustAddForm form : baseMatCustList) {
                if (!custIdSet.add(form.getCustId())) {
                    return ResponseDTO.userErrorParam("供应商列表中存在重复的供应商");
                }
            }
            // 2. 校验主供应商数量是否大于1
            long masterCount = baseMatCustList.stream()
                    .filter(form -> form.getMaster() != null && form.getMaster())
                    .count();

            if (masterCount > 1) {
                return ResponseDTO.userErrorParam(("只能有一个主供应商！"));
            }

            for (BaseMatCustAddForm baseMatCustAddForm : baseMatCustList) {
                baseMatCustAddForm.setMatId(addForm.getMatId());
            }

            List<BaseMatCustEntity> baseMatCustEntities = SmartBeanUtil.copyList(baseMatCustList, BaseMatCustEntity.class);
            baseMatCustManager.getBaseMapper().insertOrUpdate(baseMatCustEntities);
        }

        baseMatDao.insert(baseMatEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(BaseMatUpdateForm updateForm) {
        //判断物料编码是否已经存在
        if (StrUtil.isBlank(updateForm.getMatNo())) {
            String matNo = geneCodeRule(updateForm.getMatTypeId(), SmartRequestUtil.getRequestCompanyId());
            updateForm.setMatNo(matNo);
        }

        BaseMatEntity baseMatEntity = SmartBeanUtil.copy(updateForm, BaseMatEntity.class);
        LambdaQueryWrapper<BaseMatEntity> baseCustEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        baseCustEntityLambdaQueryWrapper.and(wrapper -> wrapper.eq(BaseMatEntity::getMatNo, updateForm.getMatNo())
                        .or()
                        .eq(BaseMatEntity::getMatName, updateForm.getMatName()))
                .ne(BaseMatEntity::getMatId, updateForm.getMatId());
        if (baseMatDao.selectOne(baseCustEntityLambdaQueryWrapper) != null) {
            return ResponseDTO.userErrorParam("客商编码或者客商名称已经存在");
        }

        LambdaQueryWrapper<BaseMatCustEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BaseMatCustEntity::getMatId, updateForm.getMatId());
        wrapper.eq(BaseMatCustEntity::getCompanyId, SmartRequestUtil.getRequestCompanyId());

        List<BaseMatCustAddForm> baseMatCustList = updateForm.getBaseMatCustList();
        if (!CollectionUtils.isEmpty(baseMatCustList)) {
            // 1. 校验是否有重复的供应商
            Set<Long> custIdSet = new HashSet<>();
            for (BaseMatCustAddForm form : baseMatCustList) {
                if (!custIdSet.add(form.getCustId())) {
                    return ResponseDTO.userErrorParam("供应商列表中存在重复的供应商");
                }
            }
            // 2. 校验主供应商数量是否大于1
            long masterCount = baseMatCustList.stream()
                    .filter(form -> form.getMaster() != null && form.getMaster())
                    .count();

            if (masterCount > 1) {
                return ResponseDTO.userErrorParam(("只能有一个主供应商！"));
            }

            for (BaseMatCustAddForm baseMatCustAddForm : baseMatCustList) {
                baseMatCustAddForm.setMatId(updateForm.getMatId());
            }

            List<BaseMatCustEntity> baseMatCustEntities = SmartBeanUtil.copyList(baseMatCustList, BaseMatCustEntity.class);
            List<Long> matCustIdList = baseMatCustEntities.stream().map(BaseMatCustEntity::getMatCustId).toList();
            wrapper.notIn(BaseMatCustEntity::getMatCustId, matCustIdList);
            baseMatCustManager.getBaseMapper().delete(wrapper);
            baseMatCustManager.getBaseMapper().insertOrUpdate(baseMatCustEntities);
        } else {
            baseMatCustManager.getBaseMapper().delete(wrapper);
        }

        baseMatDao.updateById(baseMatEntity);
        return ResponseDTO.ok();
    }

    public String geneCodeRule(Long matTypeId, Long companyId) {
        //1. 获取系统设置参数
        SettingVO settingVO = settingDao.findById(1L, companyId);
        //2. 根据系统设置参数生成编码规则
        String paramValue = settingVO.getParamValue();
        if (Objects.equals(paramValue, "1")) {
            //根据一级分类的编号 + 4位序列号
            BaseMatTypeVO baseMatTypeVO = matTypeDao.findTopParentById(matTypeId);
            String generate = serialNumberService.generate(SerialNumberIdEnum.BASE_MAT_FOUR, companyId);
            return baseMatTypeVO.getMatTypeCode() + generate;
        }

        if (Objects.equals(paramValue, "2")) {
            //根据一级分类的编号 + 4位序列号
            BaseMatTypeEntity baseMatTypeEntity = matTypeDao.selectById(matTypeId);
            String generate = serialNumberService.generate(SerialNumberIdEnum.BASE_MAT_FOUR, companyId);
            return baseMatTypeEntity.getMatTypeCode() + generate;
        }

        if (Objects.equals(paramValue, "3")) {
            return serialNumberService.generate(SerialNumberIdEnum.BASE_MAT_SIX, companyId);
        }
        return null;
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

        baseMatDao.batchUpdateDeleted(idList, true);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long matId) {
        if (null == matId) {
            return ResponseDTO.ok();
        }

        baseMatDao.updateDeleted(matId, true);
        return ResponseDTO.ok();
    }

    /**
     * 获取物料详情
     *
     * @param matId
     * @return
     */
    public ResponseDTO<BaseMatDetailVO> detail(Long matId) {
        if (null == matId) {
            return ResponseDTO.userErrorParam("物料ID不能为空");
        }

        BaseMatDetailVO baseMatEntity = baseMatDao.getDetailById(matId);
        if (baseMatEntity == null) {
            return ResponseDTO.userErrorParam("物料不存在");
        }

        // 根据物料id获取对应的供应商的详情
        List<BaseMatCustVO> baseMatCustEntities = baseMatCustManager.getBaseMapper().findListByMatId(matId);
        baseMatEntity.setBaseMatCustList(baseMatCustEntities);
        return ResponseDTO.ok(baseMatEntity);
    }


    public ResponseDTO<String> invalid(@Valid InvalidForm invalidForm) {
        if (CollectionUtils.isEmpty(invalidForm.getIdList())) {
            return ResponseDTO.userErrorParam("物料ID不能为空");
        }
        LambdaUpdateWrapper<BaseMatEntity> updateWrapper = new LambdaUpdateWrapper<BaseMatEntity>().
                in(BaseMatEntity::getMatId, invalidForm.getIdList()).set(BaseMatEntity::getInvalid, invalidForm.getInvalid());

        baseMatDao.update(updateWrapper);
        return ResponseDTO.ok();
    }
}
