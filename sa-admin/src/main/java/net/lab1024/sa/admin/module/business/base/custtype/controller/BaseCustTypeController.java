package net.lab1024.sa.admin.module.business.base.custtype.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.base.custtype.domain.form.BaseCustTypeAddForm;
import net.lab1024.sa.admin.module.business.base.custtype.domain.form.BaseCustTypeUpdateForm;
import net.lab1024.sa.admin.module.business.base.custtype.domain.vo.BaseCustTypeVO;
import net.lab1024.sa.admin.module.business.base.custtype.service.BaseCustTypeService;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.ValidateList;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客商类型 Controller
 *
 * @Author 赵嘉伟
 * @Date 2025-03-28 21:27:26
 * @Copyright 赵嘉伟
 */

@RestController
@Tag(name = "客商类型")
public class BaseCustTypeController {

    @Resource
    private BaseCustTypeService baseCustTypeService;

    @Operation(summary = "根据关键词查询客商分类 @author 赵嘉伟")
    @GetMapping("/baseCustType/queryByKeywords")
    public ResponseDTO<List<BaseCustTypeVO>> queryByKeywords(@RequestParam(required = false) String keywords) {
        return baseCustTypeService.queryByKeywords(keywords);
    }

    @Operation(summary = "添加 @author 赵嘉伟")
    @PostMapping("/baseCustType/add")
    public ResponseDTO<String> add(@RequestBody @Valid BaseCustTypeAddForm addForm) {
        return baseCustTypeService.add(addForm);
    }

    @Operation(summary = "更新 @author 赵嘉伟")
    @PostMapping("/baseCustType/update")
    public ResponseDTO<String> update(@RequestBody @Valid BaseCustTypeUpdateForm updateForm) {
        return baseCustTypeService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author 赵嘉伟")
    @PostMapping("/baseCustType/batchDelete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return baseCustTypeService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author 赵嘉伟")
    @GetMapping("/baseCustType/delete/{custTypeId}")
    public ResponseDTO<String> batchDelete(@PathVariable Long custTypeId) {
        return baseCustTypeService.delete(custTypeId);
    }

    @Operation(summary = "根据 custTypeId 查询客商分类详情 @author 赵嘉伟")
    @GetMapping("/baseCustType/detail/{custTypeId}")
    public ResponseDTO<BaseCustTypeVO> getDetailById(@PathVariable Long custTypeId) {
        return baseCustTypeService.getDetailById(custTypeId);
    }
}
