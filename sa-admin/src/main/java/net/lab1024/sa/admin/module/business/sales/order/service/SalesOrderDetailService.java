package net.lab1024.sa.admin.module.business.sales.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.module.business.sales.order.dao.SalesOrderDetailDao;
import net.lab1024.sa.admin.module.business.sales.order.domain.entity.SalesOrderDetailEntity;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderDetailAddForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderDetailQueryForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderDetailUpdateForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.vo.SalesOrderDetailVO;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 销售订货单明细表 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-05-25 19:36:41
 * @Copyright 赵嘉伟
 */

@Service
public class SalesOrderDetailService {

    @Resource
    private SalesOrderDetailDao salesOrderDetailDao;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<SalesOrderDetailVO> queryPage(SalesOrderDetailQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<SalesOrderDetailVO> list = salesOrderDetailDao.queryPage(page, queryForm);
        PageResult<SalesOrderDetailVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(SalesOrderDetailAddForm addForm) {
        SalesOrderDetailEntity salesOrderDetailEntity = SmartBeanUtil.copy(addForm, SalesOrderDetailEntity.class);
        salesOrderDetailDao.insert(salesOrderDetailEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(SalesOrderDetailUpdateForm updateForm) {
        SalesOrderDetailEntity salesOrderDetailEntity = SmartBeanUtil.copy(updateForm, SalesOrderDetailEntity.class);
        salesOrderDetailDao.updateById(salesOrderDetailEntity);
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

        salesOrderDetailDao.batchUpdateDeleted(idList, true);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long salesOrderDetailId) {
        if (null == salesOrderDetailId) {
            return ResponseDTO.ok();
        }

        salesOrderDetailDao.updateDeleted(salesOrderDetailId, true);
        return ResponseDTO.ok();
    }
}
