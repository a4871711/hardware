package net.lab1024.sa.admin.module.business.base.bom.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.base.bom.domain.form.BaseBomOtherAmountAddForm;
import net.lab1024.sa.admin.module.business.base.bom.domain.form.BaseBomOtherAmountQueryForm;
import net.lab1024.sa.admin.module.business.base.bom.domain.form.BaseBomOtherAmountUpdateForm;
import net.lab1024.sa.admin.module.business.base.bom.domain.vo.BaseBomOtherAmountVO;
import net.lab1024.sa.admin.module.business.base.bom.service.BaseBomOtherAmountService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.ValidateList;
import org.springframework.web.bind.annotation.*;

/**
 * 多级bom其他费用 Controller
 *
 * @Author 赵嘉伟
 * @Date 2025-04-30 21:05:57
 * @Copyright 赵嘉伟
 */

@RestController
@Tag(name = "多级bom其他费用")
public class BaseBomOtherAmountController {

    @Resource
    private BaseBomOtherAmountService baseBomOtherAmountService;

    @Operation(summary = "分页查询 @author 赵嘉伟")
    @PostMapping("/baseBomOtherAmount/queryPage")
    @SaCheckPermission("baseBomOtherAmount:query")
    public ResponseDTO<PageResult<BaseBomOtherAmountVO>> queryPage(@RequestBody @Valid BaseBomOtherAmountQueryForm queryForm) {
        return ResponseDTO.ok(baseBomOtherAmountService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author 赵嘉伟")
    @PostMapping("/baseBomOtherAmount/add")
    @SaCheckPermission("baseBomOtherAmount:add")
    public ResponseDTO<String> add(@RequestBody @Valid BaseBomOtherAmountAddForm addForm) {
        return baseBomOtherAmountService.add(addForm);
    }

    @Operation(summary = "更新 @author 赵嘉伟")
    @PostMapping("/baseBomOtherAmount/update")
    @SaCheckPermission("baseBomOtherAmount:update")
    public ResponseDTO<String> update(@RequestBody @Valid BaseBomOtherAmountUpdateForm updateForm) {
        return baseBomOtherAmountService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author 赵嘉伟")
    @PostMapping("/baseBomOtherAmount/batchDelete")
    @SaCheckPermission("baseBomOtherAmount:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return baseBomOtherAmountService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author 赵嘉伟")
    @GetMapping("/baseBomOtherAmount/delete/{bomOtherAmount}")
    @SaCheckPermission("baseBomOtherAmount:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long bomOtherAmount) {
        return baseBomOtherAmountService.delete(bomOtherAmount);
    }
}
