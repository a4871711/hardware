package net.lab1024.sa.admin.module.business.base.componentdict.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.module.business.base.componentdict.dao.ComponentDictDao;
import net.lab1024.sa.admin.module.business.base.componentdict.domain.entity.ComponentDictEntity;
import net.lab1024.sa.admin.module.business.base.componentdict.domain.form.ComponentDictAddForm;
import net.lab1024.sa.admin.module.business.base.componentdict.domain.form.ComponentDictQueryForm;
import net.lab1024.sa.admin.module.business.base.componentdict.domain.form.ComponentDictUpdateForm;
import net.lab1024.sa.admin.module.business.base.componentdict.domain.vo.ComponentDictVO;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系列组件表 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-05-01 18:07:45
 * @Copyright 赵嘉伟
 */

@Service
public class ComponentDictService {

    @Resource
    private ComponentDictDao componentDictDao;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<ComponentDictVO> queryPage(ComponentDictQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<ComponentDictVO> list = componentDictDao.queryPage(page, queryForm);
        PageResult<ComponentDictVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(ComponentDictAddForm addForm) {
        ComponentDictEntity componentDictEntity = SmartBeanUtil.copy(addForm, ComponentDictEntity.class);
        // 新增的时候，同一个typeCode的item_code 或者 item_name 不能相同
        LambdaQueryWrapper<ComponentDictEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ComponentDictEntity::getTypeCode, addForm.getTypeCode()).and(
                wrapper -> wrapper
                        .eq(ComponentDictEntity::getItemCode, addForm.getItemCode()).or()
                        .eq(ComponentDictEntity::getItemName, addForm.getItemName())
        );

        if (componentDictDao.selectOne(queryWrapper) != null) {
            return ResponseDTO.userErrorParam("typeCode 或者 itemCode 或者 itemName 已经存在");
        }


        componentDictDao.insert(componentDictEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(ComponentDictUpdateForm updateForm) {
        ComponentDictEntity componentDictEntity = SmartBeanUtil.copy(updateForm, ComponentDictEntity.class);
        //更新的时候，同一个typeCode的item_code 或者 item_name 不能相同
        LambdaQueryWrapper<ComponentDictEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(ComponentDictEntity::getComponentSeriesId, updateForm.getComponentSeriesId())
                .eq(ComponentDictEntity::getTypeCode, updateForm.getTypeCode())
                .and(wrapper -> wrapper
                        .eq(ComponentDictEntity::getItemCode, updateForm.getItemCode()).or()
                        .eq(ComponentDictEntity::getItemName, updateForm.getItemName()));
        if (componentDictDao.selectOne(queryWrapper) != null) {
            return ResponseDTO.userErrorParam("typeCode 或者 itemCode 或者 itemName 已经存在");
        }

        componentDictDao.updateById(componentDictEntity);
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

        componentDictDao.batchUpdateDeleted(idList, true);
        return ResponseDTO.ok();
    }

}
