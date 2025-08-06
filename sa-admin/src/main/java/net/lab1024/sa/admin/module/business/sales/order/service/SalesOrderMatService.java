package net.lab1024.sa.admin.module.business.sales.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.module.business.sales.order.dao.SalesOrderMatDao;
import net.lab1024.sa.admin.module.business.sales.order.domain.entity.SalesOrderMatEntity;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderMatAddForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderMatQueryForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderMatUpdateForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.vo.SalesOrderMatVO;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 销售订单原材料表 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-05-29 23:32:37
 * @Copyright 赵嘉伟
 */

@Service
public class SalesOrderMatService {

    @Resource
    private SalesOrderMatDao salesOrderMatDao;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<SalesOrderMatVO> queryPage(SalesOrderMatQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<SalesOrderMatVO> list = salesOrderMatDao.queryPage(page, queryForm);
        PageResult<SalesOrderMatVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(SalesOrderMatAddForm addForm) {
        SalesOrderMatEntity salesOrderMatEntity = SmartBeanUtil.copy(addForm, SalesOrderMatEntity.class);
        salesOrderMatDao.insert(salesOrderMatEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(SalesOrderMatUpdateForm updateForm) {
        SalesOrderMatEntity salesOrderMatEntity = SmartBeanUtil.copy(updateForm, SalesOrderMatEntity.class);
        salesOrderMatDao.updateById(salesOrderMatEntity);
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

        salesOrderMatDao.batchUpdateDeleted(idList, true);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long salesOrderMatId) {
        if (null == salesOrderMatId) {
            return ResponseDTO.ok();
        }

        salesOrderMatDao.updateDeleted(salesOrderMatId, true);
        return ResponseDTO.ok();
    }
}
