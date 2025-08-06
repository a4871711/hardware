package net.lab1024.sa.admin.module.business.base.mat.mat.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.base.mat.mat.domain.entity.BaseMatEntity;
import net.lab1024.sa.admin.module.business.base.mat.mat.domain.form.BaseMatQueryForm;
import net.lab1024.sa.admin.module.business.base.mat.mat.domain.vo.BaseMatDetailVO;
import net.lab1024.sa.admin.module.business.base.mat.mat.domain.vo.BaseMatVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 物料资料表 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-04-01 22:25:04
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface BaseMatDao extends BaseMapper<BaseMatEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<BaseMatVO> queryPage(Page page, @Param("queryForm") BaseMatQueryForm queryForm);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("matId")Long matId,@Param("deletedFlag")boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList")List<Long> idList,@Param("deletedFlag")boolean deletedFlag);

    BaseMatDetailVO getDetailById(Long matId);
}
