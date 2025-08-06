package net.lab1024.sa.admin.module.business.base.custbank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.base.custbank.domain.form.BaseCustBankAddForm;
import net.lab1024.sa.admin.module.business.base.custbank.domain.form.BaseCustBankQueryForm;
import net.lab1024.sa.admin.module.business.base.custbank.domain.form.BaseCustBankUpdateForm;
import net.lab1024.sa.admin.module.business.base.custbank.domain.vo.BaseCustBankVO;
import net.lab1024.sa.admin.module.business.base.custbank.service.BaseCustBankService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.ValidateList;
import org.springframework.web.bind.annotation.*;

/**
 * 银行客商功能表 Controller
 *
 * @Author 赵嘉伟
 * @Date 2025-03-28 23:41:32
 * @Copyright 赵嘉伟
 */

@RestController
@Tag(name = "银行客商功能表")
public class BaseCustBankController {

    @Resource
    private BaseCustBankService baseCustBankService;

    @Operation(summary = "分页查询 @author 赵嘉伟")
    @PostMapping("/baseCustBank/queryPage")
    public ResponseDTO<PageResult<BaseCustBankVO>> queryPage(@RequestBody @Valid BaseCustBankQueryForm queryForm) {
        return ResponseDTO.ok(baseCustBankService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author 赵嘉伟")
    @PostMapping("/baseCustBank/add")
    public ResponseDTO<String> add(@RequestBody @Valid BaseCustBankAddForm addForm) {
        return baseCustBankService.add(addForm);
    }

    @Operation(summary = "更新 @author 赵嘉伟")
    @PostMapping("/baseCustBank/update")
    public ResponseDTO<String> update(@RequestBody @Valid BaseCustBankUpdateForm updateForm) {
        return baseCustBankService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author 赵嘉伟")
    @PostMapping("/baseCustBank/batchDelete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return baseCustBankService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author 赵嘉伟")
    @GetMapping("/baseCustBank/delete/{custBankId}")
    public ResponseDTO<String> batchDelete(@PathVariable Long custBankId) {
        return baseCustBankService.delete(custBankId);
    }
}
