package net.lab1024.sa.admin.module.business.base.cust.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.module.business.base.bank.domain.vo.BaseBankVO;
import net.lab1024.sa.admin.module.business.base.bank.manager.BaseBankManager;
import net.lab1024.sa.admin.module.business.base.cust.dao.BaseCustDao;
import net.lab1024.sa.admin.module.business.base.cust.domain.entity.BaseCustEntity;
import net.lab1024.sa.admin.module.business.base.cust.domain.form.BaseCustAddForm;
import net.lab1024.sa.admin.module.business.base.cust.domain.form.BaseCustQueryForm;
import net.lab1024.sa.admin.module.business.base.cust.domain.form.BaseCustUpdateForm;
import net.lab1024.sa.admin.module.business.base.cust.domain.vo.BaseCustDetailVO;
import net.lab1024.sa.admin.module.business.base.cust.domain.vo.BaseCustVO;
import net.lab1024.sa.admin.module.business.base.custbank.domain.entity.BaseCustBankEntity;
import net.lab1024.sa.admin.module.business.base.custbank.manager.BaseCustBankManager;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * 客商资料 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-03-27 23:10:41
 * @Copyright 赵嘉伟
 */

@Service
public class BaseCustService {

    @Resource
    private BaseCustDao baseCustDao;

    @Resource
    private BaseCustBankManager baseCustBankManager;


    @Resource
    private BaseBankManager baseBankManager;

    public PageResult<BaseCustVO> queryPage(BaseCustQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<BaseCustVO> list = baseCustDao.queryPage(page, queryForm);
        PageResult<BaseCustVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 添加
     */
    @Transactional(rollbackFor = Throwable.class)
    public ResponseDTO<String> add(BaseCustAddForm addForm) {
        BaseCustEntity baseCustEntity = SmartBeanUtil.copy(addForm, BaseCustEntity.class);
        //判断客商编码是否已经存在
        LambdaQueryWrapper<BaseCustEntity> baseCustEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        baseCustEntityLambdaQueryWrapper.eq(BaseCustEntity::getCustNo, addForm.getCustNo()).or()
                .eq(BaseCustEntity::getCustName, addForm.getCustName());
        if (baseCustDao.selectOne(baseCustEntityLambdaQueryWrapper) != null) {
            return ResponseDTO.userErrorParam("客商编码或者客商名称已经存在");
        }

        baseCustDao.insert(baseCustEntity);
        // 处理客商银行功能表
        handleCustBank(baseCustEntity.getCustId(), addForm.getBankIdList());
        return ResponseDTO.ok();
    }

    /**
     * 更新
     */
    @Transactional(rollbackFor = Throwable.class)
    public ResponseDTO<String> update(BaseCustUpdateForm updateForm) {
        //判断客商编码是否已经存在
        LambdaQueryWrapper<BaseCustEntity> baseCustEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        baseCustEntityLambdaQueryWrapper.and(wrapper ->
                wrapper.eq(BaseCustEntity::getCustNo, updateForm.getCustNo())
                        .or()
                        .eq(BaseCustEntity::getCustName, updateForm.getCustName())
        ).ne(BaseCustEntity::getCustId, updateForm.getCustId());
        if (baseCustDao.selectOne(baseCustEntityLambdaQueryWrapper) != null) {
            return ResponseDTO.userErrorParam("客商编码或者客商名称已经存在");
        }

        BaseCustEntity baseCustEntity = SmartBeanUtil.copy(updateForm, BaseCustEntity.class);
        baseCustDao.updateById(baseCustEntity);
        // 处理客商银行功能表
        handleCustBank(baseCustEntity.getCustId(), updateForm.getBankIdList());
        return ResponseDTO.ok();
    }

    /**
     * 处理客商银行功能表
     *
     * @param custId     客商ID
     * @param bankIdList 银行ID列表
     */
    private void handleCustBank(Long custId, Set<Long> bankIdList) {
        List<BaseCustBankEntity> baseCustBankEntityList = Lists.newArrayList();
        for (Long bankId : bankIdList) {
            BaseCustBankEntity baseCustBankEntity = new BaseCustBankEntity();
            baseCustBankEntity.setCustId(custId);
            baseCustBankEntity.setBankId(bankId);
            baseCustBankEntityList.add(baseCustBankEntity);
        }
        baseCustBankManager.deleteByCustId(custId);
        if (CollUtil.isNotEmpty(baseCustBankEntityList)) {
            // 批量插入客商银行功能表
            baseCustBankManager.getBaseMapper().insert(baseCustBankEntityList);
        }
    }


    /**
     * 批量删除
     *
     * @param idList
     * @return
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        baseCustDao.batchUpdateDeleted(idList, true);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long baseCustId) {
        baseCustDao.updateDeleted(baseCustId, true);
        return ResponseDTO.ok();
    }

    /**
     * 根据 custId 查询客商详情
     */
    public ResponseDTO<BaseCustDetailVO> getDetailById(Long custId) {
        if (custId == null) {
            return ResponseDTO.userErrorParam("custId 不能为空");
        }
        BaseCustDetailVO result = baseCustDao.getDetailById(custId);
        if (result == null) {
            return ResponseDTO.userErrorParam("客商不存在");
        }
        //根据custId查询对应的银行信息
        List<BaseBankVO> bankEntityList = baseBankManager.getBankListByCustId(custId);
        result.setBankEntityList(bankEntityList);
        return ResponseDTO.ok(result);
    }

}
