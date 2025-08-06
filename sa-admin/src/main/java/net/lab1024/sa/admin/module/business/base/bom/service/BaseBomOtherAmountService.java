package net.lab1024.sa.admin.module.business.base.bom.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.module.business.base.bom.dao.BaseBomOtherAmountDao;
import net.lab1024.sa.admin.module.business.base.bom.domain.entity.BaseBomOtherAmountEntity;
import net.lab1024.sa.admin.module.business.base.bom.domain.form.BaseBomOtherAmountAddForm;
import net.lab1024.sa.admin.module.business.base.bom.domain.form.BaseBomOtherAmountQueryForm;
import net.lab1024.sa.admin.module.business.base.bom.domain.form.BaseBomOtherAmountUpdateForm;
import net.lab1024.sa.admin.module.business.base.bom.domain.vo.BaseBomOtherAmountVO;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 多级bom其他费用 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-04-30 21:05:57
 * @Copyright 赵嘉伟
 */

@Service
public class BaseBomOtherAmountService {

    @Resource
    private BaseBomOtherAmountDao baseBomOtherAmountDao;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<BaseBomOtherAmountVO> queryPage(BaseBomOtherAmountQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<BaseBomOtherAmountVO> list = baseBomOtherAmountDao.queryPage(page, queryForm);
        PageResult<BaseBomOtherAmountVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(BaseBomOtherAmountAddForm addForm) {
        BaseBomOtherAmountEntity baseBomOtherAmountEntity = SmartBeanUtil.copy(addForm, BaseBomOtherAmountEntity.class);
        baseBomOtherAmountDao.insert(baseBomOtherAmountEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(BaseBomOtherAmountUpdateForm updateForm) {
        BaseBomOtherAmountEntity baseBomOtherAmountEntity = SmartBeanUtil.copy(updateForm, BaseBomOtherAmountEntity.class);
        baseBomOtherAmountDao.updateById(baseBomOtherAmountEntity);
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

        baseBomOtherAmountDao.batchUpdateDeleted(idList, true);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long bomOtherAmount) {
        if (null == bomOtherAmount) {
            return ResponseDTO.ok();
        }

        baseBomOtherAmountDao.updateDeleted(bomOtherAmount, true);
        return ResponseDTO.ok();
    }
}
