package net.lab1024.sa.admin.module.business.base.custbank.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.business.base.custbank.dao.BaseCustBankDao;
import net.lab1024.sa.admin.module.business.base.custbank.domain.entity.BaseCustBankEntity;
import org.springframework.stereotype.Service;

/**
 * 银行客商功能表  Manager
 *
 * @Author 赵嘉伟
 * @Date 2025-03-28 23:41:32
 * @Copyright 赵嘉伟
 */
@Service
public class BaseCustBankManager extends ServiceImpl<BaseCustBankDao, BaseCustBankEntity> {

    public void deleteByCustId(Long custId) {
        LambdaQueryWrapper<BaseCustBankEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BaseCustBankEntity::getCustId, custId);
        getBaseMapper().delete(wrapper);
    }

}
