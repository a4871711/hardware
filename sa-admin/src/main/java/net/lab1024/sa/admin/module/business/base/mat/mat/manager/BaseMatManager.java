package net.lab1024.sa.admin.module.business.base.mat.mat.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lab1024.sa.admin.module.business.base.mat.mat.dao.BaseMatDao;
import net.lab1024.sa.admin.module.business.base.mat.mat.domain.entity.BaseMatEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物料资料表  Manager
 *
 * @Author 赵嘉伟
 * @Date 2025-04-01 22:25:04
 * @Copyright 赵嘉伟
 */
@Service
public class BaseMatManager extends ServiceImpl<BaseMatDao, BaseMatEntity> {

    public List<BaseMatEntity> getMatByMatTypeId(Long matTypeId) {
        LambdaQueryWrapper<BaseMatEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseMatEntity::getMatTypeId, matTypeId);
        return getBaseMapper().selectList(queryWrapper);
    }
}
