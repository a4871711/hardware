package net.lab1024.sa.admin.module.business.base.custbank.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.base.custbank.domain.entity.BaseCustBankEntity;
import net.lab1024.sa.admin.module.business.base.custbank.domain.form.BaseCustBankQueryForm;
import net.lab1024.sa.admin.module.business.base.custbank.domain.vo.BaseCustBankVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 银行客商功能表 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-03-28 23:41:32
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface BaseCustBankDao extends BaseMapper<BaseCustBankEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<BaseCustBankVO> queryPage(Page page, @Param("queryForm") BaseCustBankQueryForm queryForm);
}
