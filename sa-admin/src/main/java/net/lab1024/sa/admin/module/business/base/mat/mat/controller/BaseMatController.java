package net.lab1024.sa.admin.module.business.base.mat.mat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.base.mat.mat.domain.form.BaseMatAddForm;
import net.lab1024.sa.admin.module.business.base.mat.mat.domain.form.BaseMatQueryForm;
import net.lab1024.sa.admin.module.business.base.mat.mat.domain.form.BaseMatUpdateForm;
import net.lab1024.sa.admin.module.business.base.mat.mat.domain.form.InvalidForm;
import net.lab1024.sa.admin.module.business.base.mat.mat.domain.vo.BaseMatDetailVO;
import net.lab1024.sa.admin.module.business.base.mat.mat.domain.vo.BaseMatVO;
import net.lab1024.sa.admin.module.business.base.mat.mat.service.BaseMatService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.ValidateList;
import org.springframework.web.bind.annotation.*;

/**
 * 物料资料表 Controller
 *
 * @Author 赵嘉伟
 * @Date 2025-04-01 22:25:04
 * @Copyright 赵嘉伟
 */

@RestController
@Tag(name = "物料资料表")
public class BaseMatController {

    @Resource
    private BaseMatService baseMatService;

    @Operation(summary = "分页查询 @author 赵嘉伟")
    @PostMapping("/baseMat/queryPage")
    public ResponseDTO<PageResult<BaseMatVO>> queryPage(@RequestBody @Valid BaseMatQueryForm queryForm) {
        return ResponseDTO.ok(baseMatService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author 赵嘉伟")
    @PostMapping("/baseMat/add")
    public ResponseDTO<String> add(@RequestBody @Valid BaseMatAddForm addForm) {
        return baseMatService.add(addForm);
    }

    @Operation(summary = "更新 @author 赵嘉伟")
    @PostMapping("/baseMat/update")
    public ResponseDTO<String> update(@RequestBody @Valid BaseMatUpdateForm updateForm) {
        return baseMatService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author 赵嘉伟")
    @PostMapping("/baseMat/batchDelete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return baseMatService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author 赵嘉伟")
    @GetMapping("/baseMat/delete/{matId}")
    public ResponseDTO<String> batchDelete(@PathVariable Long matId) {
        return baseMatService.delete(matId);
    }


    @Operation(summary = "获取物料详情 @author 赵嘉伟")
    @GetMapping("/baseMat/detail/{matId}")
    public ResponseDTO<BaseMatDetailVO> detail(@PathVariable Long matId) {
        return baseMatService.detail(matId);
    }

    @Operation(summary = "作废 / 反作废 @author 赵嘉伟")
    @PostMapping("/baseMat/invalid")
    public ResponseDTO<String> invalid(@RequestBody @Valid InvalidForm invalidForm) {
        return baseMatService.invalid(invalidForm);
    }
}
