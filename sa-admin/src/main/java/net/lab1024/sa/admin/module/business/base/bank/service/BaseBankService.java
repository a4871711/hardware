package net.lab1024.sa.admin.module.business.base.bank.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.base.bank.dao.BaseBankDao;
import net.lab1024.sa.admin.module.business.base.bank.domain.entity.BaseBankEntity;
import net.lab1024.sa.admin.module.business.base.bank.domain.form.BaseBankAddForm;
import net.lab1024.sa.admin.module.business.base.bank.domain.form.BaseBankQueryForm;
import net.lab1024.sa.admin.module.business.base.bank.domain.form.BaseBankUpdateForm;
import net.lab1024.sa.admin.module.business.base.bank.domain.vo.BaseBankVO;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 银行信息 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-03-28 21:27:28
 * @Copyright 赵嘉伟
 */

@Service
public class BaseBankService {

    @Resource
    private BaseBankDao baseBankDao;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<BaseBankVO> queryPage(BaseBankQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<BaseBankVO> list = baseBankDao.queryPage(page, queryForm);
        PageResult<BaseBankVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(BaseBankAddForm addForm) {
        BaseBankEntity baseBankEntity = SmartBeanUtil.copy(addForm, BaseBankEntity.class);
        baseBankDao.insert(baseBankEntity);
        return ResponseDTO.ok();
    }

    public ResponseDTO<BaseBankVO> addCust(@Valid BaseBankAddForm addForm) {
        BaseBankEntity baseBankEntity = SmartBeanUtil.copy(addForm, BaseBankEntity.class);
        baseBankDao.insert(baseBankEntity);
        BaseBankEntity result = baseBankDao.selectById(baseBankEntity.getBankId());
        return ResponseDTO.ok(SmartBeanUtil.copy(result, BaseBankVO.class));
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(BaseBankUpdateForm updateForm) {
        BaseBankEntity baseBankEntity = SmartBeanUtil.copy(updateForm, BaseBankEntity.class);
        baseBankDao.updateById(baseBankEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     *
     * @param idList
     * @return
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        baseBankDao.batchUpdateDeleted(idList, true);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long bankId) {
        baseBankDao.updateDeleted(bankId, true);
        return ResponseDTO.ok();
    }


}
