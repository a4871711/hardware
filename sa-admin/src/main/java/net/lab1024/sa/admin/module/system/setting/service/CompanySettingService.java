package net.lab1024.sa.admin.module.system.setting.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.module.system.setting.dao.CompanySettingDao;
import net.lab1024.sa.admin.module.system.setting.domain.entity.CompanySettingEntity;
import net.lab1024.sa.admin.module.system.setting.domain.form.CompanySettingAddForm;
import net.lab1024.sa.admin.module.system.setting.domain.form.CompanySettingQueryForm;
import net.lab1024.sa.admin.module.system.setting.domain.form.CompanySettingUpdateForm;
import net.lab1024.sa.admin.module.system.setting.domain.vo.CompanySettingVO;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统参数设置-公司表 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-04-20 17:30:25
 * @Copyright 赵嘉伟
 */

@Service
public class CompanySettingService {

    @Resource
    private CompanySettingDao companySettingDao;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<CompanySettingVO> queryPage(CompanySettingQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<CompanySettingVO> list = companySettingDao.queryPage(page, queryForm);
        PageResult<CompanySettingVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(CompanySettingAddForm addForm) {
        CompanySettingEntity companySettingEntity = SmartBeanUtil.copy(addForm, CompanySettingEntity.class);
        companySettingDao.insert(companySettingEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(CompanySettingUpdateForm updateForm) {
        CompanySettingEntity companySettingEntity = SmartBeanUtil.copy(updateForm, CompanySettingEntity.class);
        companySettingDao.updateById(companySettingEntity);
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

        companySettingDao.batchUpdateDeleted(idList, true);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long companySettingId) {
        if (null == companySettingId) {
            return ResponseDTO.ok();
        }

        companySettingDao.updateDeleted(companySettingId, true);
        return ResponseDTO.ok();
    }
}
