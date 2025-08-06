package net.lab1024.sa.admin.module.business.base.color.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.base.color.domain.form.BaseColorAddForm;
import net.lab1024.sa.admin.module.business.base.color.domain.form.BaseColorQueryForm;
import net.lab1024.sa.admin.module.business.base.color.domain.form.BaseColorUpdateForm;
import net.lab1024.sa.admin.module.business.base.color.domain.vo.BaseColorVO;
import net.lab1024.sa.admin.module.business.base.color.service.BaseColorService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.ValidateList;
import org.springframework.web.bind.annotation.*;

/**
 * 颜色资料表 Controller
 *
 * @Author 赵嘉伟
 * @Date 2025-04-20 15:48:55
 * @Copyright 赵嘉伟
 */

@RestController
@Tag(name = "颜色资料表")
public class BaseColorController {

    @Resource
    private BaseColorService baseColorService;

    @Operation(summary = "分页查询 @author 赵嘉伟")
    @PostMapping("/baseColor/queryPage")
    @SaCheckPermission("baseColor:query")
    public ResponseDTO<PageResult<BaseColorVO>> queryPage(@RequestBody @Valid BaseColorQueryForm queryForm) {
        return ResponseDTO.ok(baseColorService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author 赵嘉伟")
    @PostMapping("/baseColor/add")
    @SaCheckPermission("baseColor:add")
    public ResponseDTO<String> add(@RequestBody @Valid BaseColorAddForm addForm) {
        return baseColorService.add(addForm);
    }

    @Operation(summary = "更新 @author 赵嘉伟")
    @PostMapping("/baseColor/update")
    @SaCheckPermission("baseColor:update")
    public ResponseDTO<String> update(@RequestBody @Valid BaseColorUpdateForm updateForm) {
        return baseColorService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author 赵嘉伟")
    @PostMapping("/baseColor/batchDelete")
    @SaCheckPermission("baseColor:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return baseColorService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author 赵嘉伟")
    @GetMapping("/baseColor/delete/{colorId}")
    @SaCheckPermission("baseColor:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long colorId) {
        return baseColorService.delete(colorId);
    }
}
