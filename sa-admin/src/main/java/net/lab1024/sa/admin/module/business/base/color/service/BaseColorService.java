package net.lab1024.sa.admin.module.business.base.color.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.module.business.base.color.dao.BaseColorDao;
import net.lab1024.sa.admin.module.business.base.color.domain.entity.BaseColorEntity;
import net.lab1024.sa.admin.module.business.base.color.domain.form.BaseColorAddForm;
import net.lab1024.sa.admin.module.business.base.color.domain.form.BaseColorQueryForm;
import net.lab1024.sa.admin.module.business.base.color.domain.form.BaseColorUpdateForm;
import net.lab1024.sa.admin.module.business.base.color.domain.vo.BaseColorVO;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import net.lab1024.sa.base.common.util.SmartRequestUtil;
import net.lab1024.sa.base.module.support.serialnumber.constant.SerialNumberIdEnum;
import net.lab1024.sa.base.module.support.serialnumber.service.SerialNumberService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 颜色资料表 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-04-20 15:48:55
 * @Copyright 赵嘉伟
 */

@Service
public class BaseColorService {

    @Resource
    private BaseColorDao baseColorDao;

    @Resource
    private SerialNumberService serialNumberService;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<BaseColorVO> queryPage(BaseColorQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<BaseColorVO> list = baseColorDao.queryPage(page, queryForm);
        PageResult<BaseColorVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(BaseColorAddForm addForm) {
        if (StrUtil.isBlank(addForm.getColorCode())) {
            String generate = serialNumberService.generate(SerialNumberIdEnum.COLOR_SIX, SmartRequestUtil.getRequestCompanyId());
            addForm.setColorCode(generate);
        }
        BaseColorEntity baseColorEntity = SmartBeanUtil.copy(addForm, BaseColorEntity.class);
        LambdaQueryWrapper<BaseColorEntity> baseColorEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        baseColorEntityLambdaQueryWrapper.eq(BaseColorEntity::getColorCode, addForm.getColorCode()).or()
                .eq(BaseColorEntity::getColorName, addForm.getColorName());
        if (baseColorDao.selectOne(baseColorEntityLambdaQueryWrapper) != null) {
            return ResponseDTO.userErrorParam("颜色编码或者颜色名称已经存在");
        }
        baseColorDao.insert(baseColorEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(BaseColorUpdateForm updateForm) {
        if (StrUtil.isBlank(updateForm.getColorCode())) {
            String generate = serialNumberService.generate(SerialNumberIdEnum.COLOR_SIX, SmartRequestUtil.getRequestCompanyId());
            updateForm.setColorCode(generate);
        }
        //判断客商编码是否已经存在
        LambdaQueryWrapper<BaseColorEntity> baseCustEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        baseCustEntityLambdaQueryWrapper.and(wrapper ->
                wrapper.eq(BaseColorEntity::getColorCode, updateForm.getColorCode())
                        .or()
                        .eq(BaseColorEntity::getColorName, updateForm.getColorName())
        ).ne(BaseColorEntity::getColorId, updateForm.getColorId());
        if (baseColorDao.selectOne(baseCustEntityLambdaQueryWrapper) != null) {
            return ResponseDTO.userErrorParam("颜色编码或者颜色名称已经存在");
        }

        BaseColorEntity baseColorEntity = SmartBeanUtil.copy(updateForm, BaseColorEntity.class);
        baseColorDao.updateById(baseColorEntity);
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

        baseColorDao.batchUpdateDeleted(idList, true);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long colorId) {
        if (null == colorId) {
            return ResponseDTO.ok();
        }

        baseColorDao.updateDeleted(colorId, true);
        return ResponseDTO.ok();
    }
}
