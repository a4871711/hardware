package net.lab1024.sa.admin.module.business.base.bom.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.business.base.bom.dao.BaseBomOtherAmountDao;
import net.lab1024.sa.admin.module.business.base.bom.domain.entity.BaseBomOtherAmountEntity;
import org.springframework.stereotype.Service;

/**
 * 多级bom其他费用  Manager
 *
 * @Author 赵嘉伟
 * @Date 2025-04-30 21:05:57
 * @Copyright 赵嘉伟
 */
@Service
public class BaseBomOtherAmountManager extends ServiceImpl<BaseBomOtherAmountDao, BaseBomOtherAmountEntity> {


    public void deleteByBomId(Long bomId) {
        this.lambdaUpdate()
                .eq(BaseBomOtherAmountEntity::getBomId, bomId)
                .set(BaseBomOtherAmountEntity::getDeletedFlag, true)
                .update();
    }
}
