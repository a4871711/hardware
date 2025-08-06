package net.lab1024.sa.admin.module.system.invitationcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.system.invitationcode.domain.form.CompanyInvitationCodeAddForm;
import net.lab1024.sa.admin.module.system.invitationcode.domain.form.CompanyInvitationCodeQueryForm;
import net.lab1024.sa.admin.module.system.invitationcode.domain.form.CompanyInvitationCodeUpdateForm;
import net.lab1024.sa.admin.module.system.invitationcode.domain.vo.CompanyInvitationCodeVO;
import net.lab1024.sa.admin.module.system.invitationcode.service.CompanyInvitationCodeService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.ValidateList;
import org.springframework.web.bind.annotation.*;

/**
 * 公司邀请码表 Controller
 *
 * @Author 赵嘉伟
 * @Date 2025-03-10 19:42:19
 * @Copyright 赵嘉伟
 */

@RestController
@Tag(name = "公司邀请码表")
public class CompanyInvitationCodeController {

    @Resource
    private CompanyInvitationCodeService companyInvitationCodeService;

    @Operation(summary = "分页查询 @author 赵嘉伟")
    @PostMapping("/companyInvitationCode/queryPage")
    public ResponseDTO<PageResult<CompanyInvitationCodeVO>> queryPage(
            @RequestBody @Valid CompanyInvitationCodeQueryForm queryForm) {
        return ResponseDTO.ok(companyInvitationCodeService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author 赵嘉伟")
    @PostMapping("/companyInvitationCode/add")
    @SaCheckPermission("system:companyInvitationCode:add")
    public ResponseDTO<String> add(@RequestBody @Valid CompanyInvitationCodeAddForm addForm) {
        return companyInvitationCodeService.add(addForm);
    }

    @Operation(summary = "批量删除 @author 赵嘉伟")
    @PostMapping("/companyInvitationCode/batchDelete")
    @SaCheckPermission("system:companyInvitationCode:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return companyInvitationCodeService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author 赵嘉伟")
    @GetMapping("/companyInvitationCode/delete/{invitationCodeId}")
    @SaCheckPermission("system:companyInvitationCode:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long invitationCodeId) {
        return companyInvitationCodeService.delete(invitationCodeId);
    }

    @Operation(summary = "更新 @author 赵嘉伟")
    @PostMapping("/companyInvitationCode/update")
    @SaCheckPermission("system:companyInvitationCode:update")
    public ResponseDTO<String> update(@RequestBody @Valid CompanyInvitationCodeUpdateForm updateForm) {
        return companyInvitationCodeService.update(updateForm);
    }
}
