package net.lab1024.sa.admin.module.system.company.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.system.company.domain.entity.CompanyBaseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 公司资料 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-03-05 18:27:44
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface CompanyDao extends BaseMapper<CompanyBaseEntity> {

}
