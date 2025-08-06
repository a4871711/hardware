package net.lab1024.sa.admin.module.business.base.mat.mattype.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.base.mat.mattype.domain.form.BaseMatTypeAddForm;
import net.lab1024.sa.admin.module.business.base.mat.mattype.domain.form.BaseMatTypeQueryForm;
import net.lab1024.sa.admin.module.business.base.mat.mattype.domain.form.BaseMatTypeUpdateForm;
import net.lab1024.sa.admin.module.business.base.mat.mattype.domain.vo.BaseMatTypeVO;
import net.lab1024.sa.admin.module.business.base.mat.mattype.service.BaseMatTypeService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.ValidateList;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 物料分类表 Controller
 *
 * @Author 赵嘉伟
 * @Date 2025-04-01 22:25:10
 * @Copyright 赵嘉伟
 */

@RestController
@Tag(name = "物料分类表")
public class BaseMatTypeController {

    @Resource
    private BaseMatTypeService baseMatTypeService;

    @Operation(summary = "根据关键词查询物料分类 @author 赵嘉伟")
    @GetMapping("/baseMatType/queryByKeywords")
    public ResponseDTO<List<BaseMatTypeVO>> queryByKeywords(@RequestParam(required = false) String keywords) {
        return baseMatTypeService.queryByKeywords(keywords);
    }

    @Operation(summary = "分页查询 @author 赵嘉伟")
    @PostMapping("/baseMatType/queryPage")
    public ResponseDTO<PageResult<BaseMatTypeVO>> queryPage(@RequestBody @Valid BaseMatTypeQueryForm queryForm) {
        return ResponseDTO.ok(baseMatTypeService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author 赵嘉伟")
    @PostMapping("/baseMatType/add")
    public ResponseDTO<String> add(@RequestBody @Valid BaseMatTypeAddForm addForm) {
        return baseMatTypeService.add(addForm);
    }

    @Operation(summary = "更新 @author 赵嘉伟")
    @PostMapping("/baseMatType/update")
    public ResponseDTO<String> update(@RequestBody @Valid BaseMatTypeUpdateForm updateForm) {
        return baseMatTypeService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author 赵嘉伟")
    @PostMapping("/baseMatType/batchDelete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return baseMatTypeService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author 赵嘉伟")
    @GetMapping("/baseMatType/delete/{matTypeId}")
    public ResponseDTO<String> batchDelete(@PathVariable Long matTypeId) {
        return baseMatTypeService.delete(matTypeId);
    }
}
