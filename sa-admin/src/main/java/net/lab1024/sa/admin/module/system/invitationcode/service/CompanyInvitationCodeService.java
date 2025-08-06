package net.lab1024.sa.admin.module.system.invitationcode.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.module.system.invitationcode.dao.CompanyInvitationCodeDao;
import net.lab1024.sa.admin.module.system.invitationcode.domain.entity.CompanyInvitationCodeEntity;
import net.lab1024.sa.admin.module.system.invitationcode.domain.form.CompanyInvitationCodeAddForm;
import net.lab1024.sa.admin.module.system.invitationcode.domain.form.CompanyInvitationCodeQueryForm;
import net.lab1024.sa.admin.module.system.invitationcode.domain.form.CompanyInvitationCodeUpdateForm;
import net.lab1024.sa.admin.module.system.invitationcode.domain.vo.CompanyInvitationCodeVO;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 公司邀请码表 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-03-10 19:42:19
 * @Copyright 赵嘉伟
 */

@Service
public class CompanyInvitationCodeService {

    @Resource
    private CompanyInvitationCodeDao companyInvitationCodeDao;

    /**
     * 分页查询
     *
     * @param queryForm
     * @return
     */
    public PageResult<CompanyInvitationCodeVO> queryPage(CompanyInvitationCodeQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<CompanyInvitationCodeVO> list = companyInvitationCodeDao.queryPage(page, queryForm);
        PageResult<CompanyInvitationCodeVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return pageResult;
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(CompanyInvitationCodeAddForm addForm) {
        CompanyInvitationCodeEntity companyInvitationCodeEntity = SmartBeanUtil.copy(addForm,
                CompanyInvitationCodeEntity.class);
        // 校验邀请码是否存在
        CompanyInvitationCodeEntity invitationCode = companyInvitationCodeDao
                .selectOne(new QueryWrapper<CompanyInvitationCodeEntity>()
                        .eq("invitation_code", addForm.getInvitationCode()));
        if (null != invitationCode) {
            return ResponseDTO.userErrorParam("邀请码已存在");
        }

        companyInvitationCodeDao.insert(companyInvitationCodeEntity);
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
        // 判断idList是否有对应的公司id
        List<CompanyInvitationCodeEntity> companyInvitationCodeEntities = companyInvitationCodeDao
                .selectList(new LambdaQueryWrapper<CompanyInvitationCodeEntity>()
                        .in(CompanyInvitationCodeEntity::getInvitationCodeId, idList));
        for (CompanyInvitationCodeEntity companyInvitationCodeEntity : companyInvitationCodeEntities) {
            if (Objects.nonNull(companyInvitationCodeEntity.getCompanyId())) {
                return ResponseDTO.userErrorParam("已经有对应的公司存在了，不能删除");
            }
        }
        companyInvitationCodeDao.batchUpdateDeleted(idList, true);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long invitationCodeId) {
        if (null == invitationCodeId) {
            return ResponseDTO.ok();
        }
        CompanyInvitationCodeEntity companyInvitationCodeEntity = companyInvitationCodeDao.selectById(invitationCodeId);
        if (Objects.nonNull(companyInvitationCodeEntity.getCompanyId())) {
            return ResponseDTO.userErrorParam("已经有对应的公司存在了，不能删除");
        }

        companyInvitationCodeDao.updateDeleted(invitationCodeId, true);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     */
    @Transactional(rollbackFor = Throwable.class)
    public ResponseDTO<String> update(CompanyInvitationCodeUpdateForm updateForm) {
        // 校验是否存在
        CompanyInvitationCodeEntity existEntity = companyInvitationCodeDao.selectById(updateForm.getInvitationCodeId());
        if (existEntity == null) {
            return ResponseDTO.userErrorParam("邀请码不存在");
        }

        // 校验邀请码是否重复（排除自身）
        CompanyInvitationCodeEntity invitationCode = companyInvitationCodeDao
                .selectOne(new QueryWrapper<CompanyInvitationCodeEntity>()
                        .eq("invitation_code", updateForm.getInvitationCode())
                        .ne("invitation_code_id", updateForm.getInvitationCodeId()));
        if (null != invitationCode) {
            return ResponseDTO.userErrorParam("邀请码已存在");
        }

        // 如果已经有公司使用了这个邀请码，则不允许修改订阅计划和时间
        if (Objects.nonNull(existEntity.getCompanyId())) {
            return ResponseDTO.userErrorParam("邀请码已被使用，不能修改");
        }

        CompanyInvitationCodeEntity companyInvitationCodeEntity = SmartBeanUtil.copy(updateForm,
                CompanyInvitationCodeEntity.class);
        companyInvitationCodeDao.updateById(companyInvitationCodeEntity);
        return ResponseDTO.ok();
    }
}
