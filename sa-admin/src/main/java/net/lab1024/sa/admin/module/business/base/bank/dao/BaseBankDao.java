package net.lab1024.sa.admin.module.business.base.bank.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.base.bank.domain.entity.BaseBankEntity;
import net.lab1024.sa.admin.module.business.base.bank.domain.form.BaseBankQueryForm;
import net.lab1024.sa.admin.module.business.base.bank.domain.vo.BaseBankVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 银行信息 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-03-28 21:27:28
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface BaseBankDao extends BaseMapper<BaseBankEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<BaseBankVO> queryPage(Page page, @Param("queryForm") BaseBankQueryForm queryForm);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("bankId") Long bankId, @Param("deletedFlag") boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList") List<Long> idList, @Param("deletedFlag") boolean deletedFlag);

    List<BaseBankVO> getBankListByCustId(Long custId);
}
