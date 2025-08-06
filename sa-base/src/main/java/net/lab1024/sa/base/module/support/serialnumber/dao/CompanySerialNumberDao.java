package net.lab1024.sa.base.module.support.serialnumber.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.base.module.support.serialnumber.domain.CompanySerialNumberEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 公司自定义单号生成器定义表 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-04-08 19:02:04
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface CompanySerialNumberDao extends BaseMapper<CompanySerialNumberEntity> {

    List<CompanySerialNumberEntity> findAllBySerialNumberId(List<Integer> serialNumberIdList);

    void updateLastNumberAndTime(@Param("serialNumberId") Integer serialNumberId,
                                 @Param("companyId") Long companyId,
                                 @Param("lastNumber") Long lastNumber,
                                 @Param("lastTime") LocalDateTime lastTime);

}