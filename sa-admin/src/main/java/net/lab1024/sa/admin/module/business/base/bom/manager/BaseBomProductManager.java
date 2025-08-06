package net.lab1024.sa.admin.module.business.base.bom.manager;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.business.base.bom.dao.BaseBomProductDao;
import net.lab1024.sa.admin.module.business.base.bom.domain.entity.BaseBomProductEntity;
import org.springframework.stereotype.Service;

/**
 * 多级BOM-产品材料  Manager
 *
 * @Author 赵嘉伟
 * @Date 2025-04-30 21:05:57
 * @Copyright 赵嘉伟
 */
@Service
public class BaseBomProductManager extends ServiceImpl<BaseBomProductDao, BaseBomProductEntity> {


    public void deleteByBomId(Long bomId) {
        LambdaUpdateWrapper<BaseBomProductEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(BaseBomProductEntity::getBomId, bomId);
        wrapper.set(BaseBomProductEntity::getDeletedFlag, true);
        this.update(null, wrapper);
    }
}
