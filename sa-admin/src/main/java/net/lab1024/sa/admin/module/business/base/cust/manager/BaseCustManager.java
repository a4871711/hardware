package net.lab1024.sa.admin.module.business.base.cust.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.business.base.cust.dao.BaseCustDao;
import net.lab1024.sa.admin.module.business.base.cust.domain.entity.BaseCustEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客商资料  Manager
 *
 * @Author 赵嘉伟
 * @Date 2025-03-27 23:10:41
 * @Copyright 赵嘉伟
 */
@Service
public class BaseCustManager extends ServiceImpl<BaseCustDao, BaseCustEntity> {

    /**
     * 根据客商分类获取客商
     */
    public List<BaseCustEntity> getCustByCustTypeId(Long custTypeId) {
        LambdaQueryWrapper<BaseCustEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseCustEntity::getCustTypeId, custTypeId);
        return list(queryWrapper);
    }

}
