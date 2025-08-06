package net.lab1024.sa.admin.module.business.sales.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.module.business.sales.order.dao.SalesOrderRequirementsDao;
import net.lab1024.sa.admin.module.business.sales.order.domain.entity.SalesOrderRequirementsEntity;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderRequirementsAddForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderRequirementsQueryForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderRequirementsUpdateForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.vo.SalesOrderRequirementsVO;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 销售订单-订单要求表 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-05-25 19:36:51
 * @Copyright 赵嘉伟
 */

@Service
public class SalesOrderRequirementsService {

    @Resource
    private SalesOrderRequirementsDao salesOrderRequirementsDao;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<SalesOrderRequirementsVO> queryPage(SalesOrderRequirementsQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<SalesOrderRequirementsVO> list = salesOrderRequirementsDao.queryPage(page, queryForm);
        PageResult<SalesOrderRequirementsVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(SalesOrderRequirementsAddForm addForm) {
        SalesOrderRequirementsEntity salesOrderRequirementsEntity = SmartBeanUtil.copy(addForm, SalesOrderRequirementsEntity.class);
        salesOrderRequirementsDao.insert(salesOrderRequirementsEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(SalesOrderRequirementsUpdateForm updateForm) {
        SalesOrderRequirementsEntity salesOrderRequirementsEntity = SmartBeanUtil.copy(updateForm, SalesOrderRequirementsEntity.class);
        salesOrderRequirementsDao.updateById(salesOrderRequirementsEntity);
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

        salesOrderRequirementsDao.batchUpdateDeleted(idList, true);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long salesOrderRequirementsId) {
        if (null == salesOrderRequirementsId) {
            return ResponseDTO.ok();
        }

        salesOrderRequirementsDao.updateDeleted(salesOrderRequirementsId, true);
        return ResponseDTO.ok();
    }
}
