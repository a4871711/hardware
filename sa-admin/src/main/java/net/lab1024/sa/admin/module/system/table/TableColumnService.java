package net.lab1024.sa.admin.module.system.table;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeBaseEntity;
import net.lab1024.sa.admin.module.system.employee.domain.vo.EmployeeVO;
import net.lab1024.sa.admin.module.system.employee.manager.EmployeeManager;
import net.lab1024.sa.admin.module.system.role.dao.RoleDao;
import net.lab1024.sa.admin.module.system.role.domain.entity.RoleBaseEntity;
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleVO;
import net.lab1024.sa.admin.module.system.table.domain.TableColumnEntity;
import net.lab1024.sa.admin.module.system.table.domain.TableColumnItemForm;
import net.lab1024.sa.admin.module.system.table.domain.TableColumnUpdateForm;
import net.lab1024.sa.admin.module.system.table.domain.entity.TableRoleEntity;
import net.lab1024.sa.admin.module.system.table.domain.form.TableRoleAddForm;
import net.lab1024.sa.admin.module.system.table.domain.vo.TableRoleVO;
import net.lab1024.sa.base.common.domain.RequestUser;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 表格自定义列（前端用户自定义表格列，并保存到数据库里）
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-08-12 22:52:21
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a>
 */
@Service
public class TableColumnService {

    @Resource
    private TableColumnDao tableColumnDao;

    @Resource
    private TableRoleDao tableRoleDao;

    @Resource
    private RoleDao roleDao;

    @Resource
    private EmployeeManager employeeManager;

    /**
     * 获取 - 表格列
     *
     * @return
     */
    public String getTableColumns(RequestUser requestUser, Integer tableId) {
        // 1. 获取表列配置
        TableColumnEntity tableColumnEntity = tableColumnDao.selectByUserIdAndTableId(
                requestUser.getUserId(),
                requestUser.getUserType().getValue(),
                tableId);

        // 2. 获取表角色配置
        List<TableRoleVO> tableRoleList = getTableRoles(tableId);

        // 3. 确定隐藏列
        Set<TableRoleVO> hiddenColumns = determineHiddenColumns(requestUser, tableRoleList);

        // 4. 处理列数据
        List<TableColumnItemForm> columns = processColumns(tableColumnEntity, hiddenColumns, requestUser,
                tableRoleList);
        return JSONUtil.toJsonStr(columns);
    }

    private List<TableRoleVO> getTableRoles(Integer tableId) {
        // 查询表角色信息
        var tableRoles = tableRoleDao.selectListByTableId(tableId, false);

        // 1. 处理角色信息
        var roleIds = tableRoles.stream()
                .map(TableRoleVO::getRoleId)
                .filter(Objects::nonNull)
                .filter(Predicate.not(String::isBlank))
                .flatMap(roleId -> Arrays.stream(roleId.split(",")))
                .map(Long::parseLong)
                .collect(Collectors.toSet());

        if (!roleIds.isEmpty()) {
            var roleMap = roleDao.selectList(new LambdaQueryWrapper<RoleBaseEntity>()
                            .in(RoleBaseEntity::getRoleId, roleIds))
                    .stream()
                    .map(role -> SmartBeanUtil.copy(role, RoleVO.class))
                    .collect(Collectors.toMap(RoleVO::getRoleId, Function.identity()));

            tableRoles.forEach(tableRole -> {
                var roles = Optional.ofNullable(tableRole.getRoleId())
                        .filter(Predicate.not(String::isBlank))
                        .stream()
                        .flatMap(roleId -> Arrays.stream(roleId.split(",")))
                        .map(Long::parseLong)
                        .map(roleMap::get)
                        .filter(Objects::nonNull)
                        .toList();
                tableRole.setRoleList(roles);
            });
        } else {
            tableRoles.forEach(tr -> tr.setRoleList(List.of()));
        }

        // 2. 处理员工信息
        var employeeIdSet = tableRoles.stream()
                .map(TableRoleVO::getEmployeeId)
                .filter(Objects::nonNull)
                .filter(Predicate.not(String::isBlank))
                .flatMap(employeeId -> Arrays.stream(employeeId.split(",")))
                .map(Long::parseLong)
                .collect(Collectors.toSet());

        if (!employeeIdSet.isEmpty()) {
            var employeeMap = employeeManager.getBaseMapper()
                    .selectList(new LambdaQueryWrapper<EmployeeBaseEntity>()
                            .in(EmployeeBaseEntity::getEmployeeId, employeeIdSet))
                    .stream()
                    .map(employee -> SmartBeanUtil.copy(employee, EmployeeVO.class))
                    .collect(Collectors.toMap(EmployeeVO::getEmployeeId, Function.identity()));

            tableRoles.forEach(tableRole -> {
                var employees = Optional.ofNullable(tableRole.getEmployeeId())
                        .filter(Predicate.not(String::isBlank))
                        .stream()
                        .flatMap(employeeId -> Arrays.stream(employeeId.split(",")))
                        .map(Long::parseLong)
                        .map(employeeMap::get)
                        .filter(Objects::nonNull)
                        .toList();
                tableRole.setEmployeeList(employees);
            });
        } else {
            tableRoles.forEach(tr -> tr.setEmployeeList(List.of()));
        }

        return tableRoles;
    }

    private Set<TableRoleVO> determineHiddenColumns(RequestUser requestUser, List<TableRoleVO> tableRoleList) {
        Set<TableRoleVO> hiddenColumns = new HashSet<>();
        List<Long> roleIdList = requestUser.getRoldIdList();
        String userIdStr = requestUser.getUserId().toString();

        for (TableRoleVO tableRole : tableRoleList) {
            if (StrUtil.isNotBlank(tableRole.getEmployeeId()) &&
                    tableRole.getEmployeeId().contains(userIdStr)) {
                hiddenColumns.add(tableRole);
                continue;
            }

            String roleId = tableRole.getRoleId();
            if (StrUtil.isNotBlank(roleId) && hasAllRequiredRoles(roleIdList, roleId)) {
                hiddenColumns.add(tableRole);
            }
        }
        return hiddenColumns;
    }

    private boolean hasAllRequiredRoles(List<Long> userRoles, String requiredRoles) {
        return userRoles.stream()
                .allMatch(role -> requiredRoles.contains(role.toString()));
    }

    private List<TableColumnItemForm> processColumns(TableColumnEntity tableColumnEntity,
                                                     Set<TableRoleVO> hiddenColumns, RequestUser requestUser, List<TableRoleVO> tableRoleList) {
        // 初始化 columns
        List<TableColumnItemForm> columns = tableColumnEntity == null
                ? new ArrayList<>()
                : JSONUtil.toList(tableColumnEntity.getColumns(), TableColumnItemForm.class);

        // 创建 hiddenColumns 和 tableRoleList 的 Map 缓存，key 为 columns 值
        Map<String, TableRoleVO> hiddenColumnsMap = hiddenColumns.stream()
                .collect(Collectors.toMap(TableRoleVO::getColumns, Function.identity(), (v1, v2) -> v1));
        Map<String, TableRoleVO> tableRoleMap = tableRoleList.stream()
                .collect(Collectors.toMap(TableRoleVO::getColumns, Function.identity(), (v1, v2) -> v1));

        // 单次遍历处理列
        columns.forEach(col -> {
            String field = col.getField();

            // 处理 hiddenColumns 匹配
            TableRoleVO hiddenCol = hiddenColumnsMap.get(field);
            if (hiddenCol != null) {
                col.setRoleList(hiddenCol.getRoleList());
                col.setRoleId(hiddenCol.getRoleId());
                col.setEmployeeList(hiddenCol.getEmployeeList());
                col.setHidden(!Boolean.TRUE.equals(requestUser.getAdministratorFlag()));
            }

            // 处理 tableRoleList 匹配
            TableRoleVO tableRole = tableRoleMap.get(field);
            if (tableRole != null) {
                col.setRoleList(tableRole.getRoleList());
                col.setRoleId(tableRole.getRoleId());
                col.setEmployeeList(tableRole.getEmployeeList());
            }
        });

        return columns;
    }

    /**
     * 更新表格列
     *
     * @return
     */
    public ResponseDTO<String> updateTableColumns(RequestUser requestUser, TableColumnUpdateForm updateForm) {
        // 1. 参数校验
        if (CollectionUtils.isEmpty(updateForm.getColumnList())) {
            return ResponseDTO.ok();
        }

        // 2. 更新表格列配置
        Integer tableId = updateForm.getTableId();
        TableColumnEntity tableColumnEntity = getOrCreateTableColumnEntity(requestUser, tableId);
        updateTableColumnData(tableColumnEntity, updateForm.getColumnList());

        // 3. 更新表格角色配置
        if (CollectionUtil.isNotEmpty(updateForm.getTableRoleAddFormList())) {
            updateTableRoles(tableId, updateForm.getTableRoleAddFormList());
        }

        return ResponseDTO.ok();
    }

    private TableColumnEntity getOrCreateTableColumnEntity(RequestUser requestUser, Integer tableId) {
        TableColumnEntity entity = tableColumnDao.selectByUserIdAndTableId(
                requestUser.getUserId(),
                requestUser.getUserType().getValue(),
                tableId);

        if (entity == null) {
            entity = new TableColumnEntity();
            entity.setTableId(tableId);
            entity.setUserId(requestUser.getUserId());
            entity.setUserType(requestUser.getUserType().getValue());
            tableColumnDao.insert(entity);
        }
        return entity;
    }

    // 辅助方法：更新表格列数据
    private void updateTableColumnData(TableColumnEntity entity, List<TableColumnItemForm> columnList) {
        entity.setColumns(JSONArray.toJSONString(columnList));
        tableColumnDao.updateById(entity);
    }

    private void updateTableRoles(Integer tableId, List<TableRoleAddForm> roleAddForms) {
        // 获取现有角色配置
        LambdaQueryWrapper<TableRoleEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TableRoleEntity::getTableId, tableId);
        List<TableRoleEntity> existingRoles = tableRoleDao.selectList(wrapper);

        // 准备批量更新/插入的数据
        List<TableRoleEntity> batchData = prepareBatchRoleData(tableId, roleAddForms, existingRoles);

        // 执行批量操作
        if (CollectionUtils.isNotEmpty(batchData)) {
            tableRoleDao.insertOrUpdate(batchData);
        }
    }

    private List<TableRoleEntity> prepareBatchRoleData(Integer tableId,
                                                       List<TableRoleAddForm> roleAddForms, List<TableRoleEntity> existingRoles) {
        Map<String, TableRoleEntity> existingRoleMap = existingRoles.stream()
                .collect(Collectors.toMap(TableRoleEntity::getColumns, Function.identity()));

        return roleAddForms.stream()
                .map(form -> {
                    TableRoleEntity entity = existingRoleMap.get(form.getColumns());
                    if (entity != null) {
                        // 更新现有记录
                        entity.setRoleId(form.getRoleId());
                        entity.setEmployeeId(form.getEmployeeId());
                    } else {
                        // 创建新记录
                        entity = SmartBeanUtil.copy(form, TableRoleEntity.class);
                        entity.setTableId(tableId);
                    }
                    return entity;
                })
                .collect(Collectors.toList());
    }

    /**
     * 删除表格列
     *
     * @return
     */
    public ResponseDTO<String> deleteTableColumn(RequestUser requestUser, Integer tableId) {
        tableColumnDao.deleteTableColumn(requestUser.getUserId(), requestUser.getUserType().getValue(), tableId);
        return ResponseDTO.ok();
    }
}
