package net.lab1024.sa.admin.module.system.setting.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.module.system.setting.dao.CompanySettingDao;
import net.lab1024.sa.admin.module.system.setting.dao.SettingDao;
import net.lab1024.sa.admin.module.system.setting.domain.entity.CompanySettingEntity;
import net.lab1024.sa.admin.module.system.setting.domain.form.CompanySettingUpdateForm;
import net.lab1024.sa.admin.module.system.setting.domain.form.SettingQueryForm;
import net.lab1024.sa.admin.module.system.setting.domain.vo.SettingVO;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import net.lab1024.sa.base.common.util.SmartRequestUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 系统参数设置-主表 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-04-20 17:30:24
 * @Copyright 赵嘉伟
 */

@Service
public class SettingService {

    @Resource
    private SettingDao settingDao;

    @Resource
    private CompanySettingDao companySettingDao;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<SettingVO> queryPage(SettingQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        Long companyId = SmartRequestUtil.getRequestCompanyId();
        List<SettingVO> list = settingDao.queryPage(page, queryForm, companyId);
        PageResult<SettingVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> updateAndInsert(CompanySettingUpdateForm companySettingUpdateForm) {
        CompanySettingEntity companySettingEntity = SmartBeanUtil.copy(companySettingUpdateForm, CompanySettingEntity.class);
        if (Objects.isNull(companySettingUpdateForm.getCompanySettingId())) {
            companySettingDao.insert(companySettingEntity);
        } else {
            companySettingDao.updateById(companySettingEntity);
        }
        return ResponseDTO.ok();
    }

}
