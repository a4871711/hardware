package net.lab1024.sa.admin.module.system.setting.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.system.setting.domain.form.CompanySettingUpdateForm;
import net.lab1024.sa.admin.module.system.setting.domain.form.SettingQueryForm;
import net.lab1024.sa.admin.module.system.setting.domain.vo.SettingVO;
import net.lab1024.sa.admin.module.system.setting.service.SettingService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统参数设置-主表 Controller
 *
 * @Author 赵嘉伟
 * @Date 2025-04-20 17:30:24
 * @Copyright 赵嘉伟
 */

@RestController
@Tag(name = "系统参数设置-主表")
public class SettingController {

    @Resource
    private SettingService settingService;

    @Operation(summary = "分页查询 @author 赵嘉伟")
    @PostMapping("/setting/queryPage")
    @SaCheckPermission("setting:query")
    public ResponseDTO<PageResult<SettingVO>> queryPage(@RequestBody @Valid SettingQueryForm queryForm) {
        return ResponseDTO.ok(settingService.queryPage(queryForm));
    }

    @Operation(summary = "更新 @author 赵嘉伟")
    @PostMapping("/setting/updateAndInsert")
    @SaCheckPermission("setting:updateAndInsert")
    public ResponseDTO<String> updateAndInsert(@RequestBody @Valid CompanySettingUpdateForm companySettingUpdateForm) {
        return settingService.updateAndInsert(companySettingUpdateForm);
    }
}
