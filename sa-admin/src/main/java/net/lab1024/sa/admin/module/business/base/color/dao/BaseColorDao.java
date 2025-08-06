package net.lab1024.sa.admin.module.business.base.color.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.base.color.domain.entity.BaseColorEntity;
import net.lab1024.sa.admin.module.business.base.color.domain.form.BaseColorQueryForm;
import net.lab1024.sa.admin.module.business.base.color.domain.vo.BaseColorVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 颜色资料表 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-04-20 15:48:55
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface BaseColorDao extends BaseMapper<BaseColorEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<BaseColorVO> queryPage(Page page, @Param("queryForm") BaseColorQueryForm queryForm);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("colorId") Long colorId, @Param("deletedFlag") boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList") List<Long> idList, @Param("deletedFlag") boolean deletedFlag);

}
