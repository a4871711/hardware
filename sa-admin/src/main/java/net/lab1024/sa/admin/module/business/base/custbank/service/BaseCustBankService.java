package net.lab1024.sa.admin.module.business.base.custbank.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.module.business.base.custbank.dao.BaseCustBankDao;
import net.lab1024.sa.admin.module.business.base.custbank.domain.entity.BaseCustBankEntity;
import net.lab1024.sa.admin.module.business.base.custbank.domain.form.BaseCustBankAddForm;
import net.lab1024.sa.admin.module.business.base.custbank.domain.form.BaseCustBankQueryForm;
import net.lab1024.sa.admin.module.business.base.custbank.domain.form.BaseCustBankUpdateForm;
import net.lab1024.sa.admin.module.business.base.custbank.domain.vo.BaseCustBankVO;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 银行客商功能表 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-03-28 23:41:32
 * @Copyright 赵嘉伟
 */

@Service
public class BaseCustBankService {

    @Resource
    private BaseCustBankDao baseCustBankDao;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<BaseCustBankVO> queryPage(BaseCustBankQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<BaseCustBankVO> list = baseCustBankDao.queryPage(page, queryForm);
        PageResult<BaseCustBankVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(BaseCustBankAddForm addForm) {
        BaseCustBankEntity baseCustBankEntity = SmartBeanUtil.copy(addForm, BaseCustBankEntity.class);
        baseCustBankDao.insert(baseCustBankEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(BaseCustBankUpdateForm updateForm) {
        BaseCustBankEntity baseCustBankEntity = SmartBeanUtil.copy(updateForm, BaseCustBankEntity.class);
        baseCustBankDao.updateById(baseCustBankEntity);
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

        baseCustBankDao.deleteBatchIds(idList);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long custBankId) {
        if (null == custBankId) {
            return ResponseDTO.ok();
        }

        baseCustBankDao.deleteById(custBankId);
        return ResponseDTO.ok();
    }
}
