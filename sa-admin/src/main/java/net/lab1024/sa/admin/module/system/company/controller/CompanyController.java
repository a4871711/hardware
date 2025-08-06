package net.lab1024.sa.admin.module.system.company.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.system.company.domain.form.CompanyAddForm;
import net.lab1024.sa.admin.module.system.company.domain.form.CompanyUpdateForm;
import net.lab1024.sa.admin.module.system.company.domain.vo.CompanyVO;
import net.lab1024.sa.admin.module.system.company.service.CompanyService;
import net.lab1024.sa.base.common.annoation.NoNeedLogin;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公司资料 Controller
 *
 * @Author 赵嘉伟
 * @Date 2025-03-05 18:27:44
 * @Copyright 赵嘉伟
 */

@RestController
@Tag(name = "公司资料")
public class CompanyController {

    @Resource
    private CompanyService companyService;

    @Operation(summary = "查询当前用户公司信息 @author 赵嘉伟")
    @PostMapping("/company/query")
    public ResponseDTO<CompanyVO> query() {
        return companyService.query();
    }

    @NoNeedLogin
    @Operation(summary = "公司注册 @author 赵嘉伟")
    @PostMapping("/company/add")
    public ResponseDTO<String> add(@RequestBody @Valid CompanyAddForm addForm) {
        return companyService.add(addForm);
    }

    @Operation(summary = "更新 @author 赵嘉伟")
    @PostMapping("/company/update")
    @SaCheckPermission("system:company:update")
    public ResponseDTO<String> update(@RequestBody @Valid CompanyUpdateForm updateForm) {
        return companyService.update(updateForm);
    }

}
