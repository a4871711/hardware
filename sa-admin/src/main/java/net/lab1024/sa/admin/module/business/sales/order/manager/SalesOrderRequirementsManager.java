package net.lab1024.sa.admin.module.business.sales.order.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.business.sales.order.dao.SalesOrderRequirementsDao;
import net.lab1024.sa.admin.module.business.sales.order.domain.entity.SalesOrderRequirementsEntity;
import net.lab1024.sa.admin.module.business.sales.order.domain.vo.SalesOrderRequirementsVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 销售订单-订单要求表  Manager
 *
 * @Author 赵嘉伟
 * @Date 2025-05-25 19:36:51
 * @Copyright 赵嘉伟
 */
@Service
public class SalesOrderRequirementsManager extends ServiceImpl<SalesOrderRequirementsDao, SalesOrderRequirementsEntity> {


    public List<SalesOrderRequirementsVO> findById(Long salesOrderId) {
        return baseMapper.findById(salesOrderId);
    }
}
