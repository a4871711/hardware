package net.lab1024.sa.admin.module.system.invitationcode.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.invitationcode.domain.entity.CompanyInvitationCodeEntity;
import net.lab1024.sa.admin.module.system.invitationcode.domain.form.CompanyInvitationCodeQueryForm;
import net.lab1024.sa.admin.module.system.invitationcode.domain.vo.CompanyInvitationCodeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 公司邀请码表 Dao
 *
 * @Author 赵嘉伟
 * @Date 2025-03-10 19:42:19
 * @Copyright 赵嘉伟
 */

@Mapper
@Component
public interface CompanyInvitationCodeDao extends BaseMapper<CompanyInvitationCodeEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<CompanyInvitationCodeVO> queryPage(Page page, @Param("queryForm") CompanyInvitationCodeQueryForm queryForm);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("invitationCodeId") Long invitationCodeId, @Param("deletedFlag") boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList") List<Long> idList, @Param("deletedFlag") boolean deletedFlag);

}
