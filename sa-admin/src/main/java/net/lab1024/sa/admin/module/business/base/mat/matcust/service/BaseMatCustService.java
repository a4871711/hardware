package net.lab1024.sa.admin.module.business.base.mat.matcust.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.module.business.base.mat.matcust.dao.BaseMatCustDao;
import net.lab1024.sa.admin.module.business.base.mat.matcust.domain.entity.BaseMatCustEntity;
import net.lab1024.sa.admin.module.business.base.mat.matcust.domain.form.BaseMatCustAddForm;
import net.lab1024.sa.admin.module.business.base.mat.matcust.domain.form.BaseMatCustQueryForm;
import net.lab1024.sa.admin.module.business.base.mat.matcust.domain.form.BaseMatCustUpdateForm;
import net.lab1024.sa.admin.module.business.base.mat.matcust.domain.vo.BaseMatCustVO;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物料供应商单价表 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-04-22 00:40:08
 * @Copyright 赵嘉伟
 */

@Service
public class BaseMatCustService {

    @Resource
    private BaseMatCustDao baseMatCustDao;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<BaseMatCustVO> queryPage(BaseMatCustQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<BaseMatCustVO> list = baseMatCustDao.queryPage(page, queryForm);
        PageResult<BaseMatCustVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(BaseMatCustAddForm addForm) {
        //TODO 判断该物料供应商单价表编码是否已经存在


        BaseMatCustEntity baseMatCustEntity = SmartBeanUtil.copy(addForm, BaseMatCustEntity.class);
        baseMatCustDao.insert(baseMatCustEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(BaseMatCustUpdateForm updateForm) {
        //TODO 判断该物料供应商单价表编码是否已经存在


        BaseMatCustEntity baseMatCustEntity = SmartBeanUtil.copy(updateForm, BaseMatCustEntity.class);
        baseMatCustDao.updateById(baseMatCustEntity);
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

        baseMatCustDao.batchUpdateDeleted(idList, true);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long matCustId) {
        if (null == matCustId) {
            return ResponseDTO.ok();
        }

        baseMatCustDao.updateDeleted(matCustId, true);
        return ResponseDTO.ok();
    }
}
