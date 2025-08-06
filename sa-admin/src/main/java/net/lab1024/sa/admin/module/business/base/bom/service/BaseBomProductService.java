package net.lab1024.sa.admin.module.business.base.bom.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.module.business.base.bom.dao.BaseBomProductDao;
import net.lab1024.sa.admin.module.business.base.bom.domain.entity.BaseBomProductEntity;
import net.lab1024.sa.admin.module.business.base.bom.domain.form.BaseBomProductAddForm;
import net.lab1024.sa.admin.module.business.base.bom.domain.form.BaseBomProductQueryForm;
import net.lab1024.sa.admin.module.business.base.bom.domain.form.BaseBomProductUpdateForm;
import net.lab1024.sa.admin.module.business.base.bom.domain.vo.BaseBomProductVO;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 多级BOM-产品材料 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-04-30 21:05:57
 * @Copyright 赵嘉伟
 */

@Service
public class BaseBomProductService {

    @Resource
    private BaseBomProductDao baseBomProductDao;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<BaseBomProductVO> queryPage(BaseBomProductQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<BaseBomProductVO> list = baseBomProductDao.queryPage(page, queryForm);
        PageResult<BaseBomProductVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(BaseBomProductAddForm addForm) {
        BaseBomProductEntity baseBomProductEntity = SmartBeanUtil.copy(addForm, BaseBomProductEntity.class);
        baseBomProductDao.insert(baseBomProductEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(BaseBomProductUpdateForm updateForm) {
        BaseBomProductEntity baseBomProductEntity = SmartBeanUtil.copy(updateForm, BaseBomProductEntity.class);
        baseBomProductDao.updateById(baseBomProductEntity);
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

        baseBomProductDao.batchUpdateDeleted(idList, true);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long bomProductId) {
        if (null == bomProductId) {
            return ResponseDTO.ok();
        }

        baseBomProductDao.updateDeleted(bomProductId, true);
        return ResponseDTO.ok();
    }
}
