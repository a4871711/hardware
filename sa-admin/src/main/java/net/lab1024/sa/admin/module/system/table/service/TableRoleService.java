package net.lab1024.sa.admin.module.system.table.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.module.system.table.TableRoleDao;
import net.lab1024.sa.admin.module.system.table.domain.entity.TableRoleEntity;
import net.lab1024.sa.admin.module.system.table.domain.form.TableRoleAddForm;
import net.lab1024.sa.admin.module.system.table.domain.form.TableRoleQueryForm;
import net.lab1024.sa.admin.module.system.table.domain.form.TableRoleUpdateForm;
import net.lab1024.sa.admin.module.system.table.domain.vo.TableRoleVO;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 表格的列所隐藏的角色 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-04-04 23:12:00
 * @Copyright 赵嘉伟
 */

@Service
public class TableRoleService {

    @Resource
    private TableRoleDao tableRoleDao;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<TableRoleVO> queryPage(TableRoleQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<TableRoleVO> list = tableRoleDao.queryPage(page, queryForm);
        PageResult<TableRoleVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(TableRoleAddForm addForm) {
        TableRoleEntity tableRoleEntity = SmartBeanUtil.copy(addForm, TableRoleEntity.class);
        tableRoleDao.insert(tableRoleEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(TableRoleUpdateForm updateForm) {
        TableRoleEntity tableRoleEntity = SmartBeanUtil.copy(updateForm, TableRoleEntity.class);
        tableRoleDao.updateById(tableRoleEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     *
     * @param idList
     * @return
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return ResponseDTO.ok();
        }

        tableRoleDao.batchUpdateDeleted(idList, true);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long tableRoleId) {
        if (null == tableRoleId) {
            return ResponseDTO.ok();
        }

        tableRoleDao.updateDeleted(tableRoleId, true);
        return ResponseDTO.ok();
    }
}
