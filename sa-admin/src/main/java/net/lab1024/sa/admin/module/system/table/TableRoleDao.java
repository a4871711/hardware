package net.lab1024.sa.admin.module.system.table;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.table.domain.entity.TableRoleEntity;
import net.lab1024.sa.admin.module.system.table.domain.form.TableRoleQueryForm;
import net.lab1024.sa.admin.module.system.table.domain.vo.TableRoleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 表格的列所隐藏的角色 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-04-04 23:12:00
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface TableRoleDao extends BaseMapper<TableRoleEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<TableRoleVO> queryPage(Page page, @Param("queryForm") TableRoleQueryForm queryForm);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("tableRoleId") Long tableRoleId, @Param("deletedFlag") boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList") List<Long> idList, @Param("deletedFlag") boolean deletedFlag);

    List<TableRoleVO> selectListByTableId(@Param("tableId") Integer tableId, @Param("deletedFlag") boolean deletedFlag);

}
