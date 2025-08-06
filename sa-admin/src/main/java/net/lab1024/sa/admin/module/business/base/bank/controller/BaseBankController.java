package net.lab1024.sa.admin.module.business.base.bank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.base.bank.domain.form.BaseBankAddForm;
import net.lab1024.sa.admin.module.business.base.bank.domain.form.BaseBankQueryForm;
import net.lab1024.sa.admin.module.business.base.bank.domain.form.BaseBankUpdateForm;
import net.lab1024.sa.admin.module.business.base.bank.domain.vo.BaseBankVO;
import net.lab1024.sa.admin.module.business.base.bank.service.BaseBankService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.ValidateList;
import org.springframework.web.bind.annotation.*;

/**
 * 银行信息 Controller
 *
 * @Author 赵嘉伟
 * @Date 2025-03-28 21:27:28
 * @Copyright 赵嘉伟
 */

@RestController
@Tag(name = "银行信息")
public class BaseBankController {

    @Resource
    private BaseBankService baseBankService;

    @Operation(summary = "分页查询 @author 赵嘉伟")
    @PostMapping("/baseBank/queryPage")
    public ResponseDTO<PageResult<BaseBankVO>> queryPage(@RequestBody @Valid BaseBankQueryForm queryForm) {
        return ResponseDTO.ok(baseBankService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author 赵嘉伟")
    @PostMapping("/baseBank/add")
    public ResponseDTO<String> add(@RequestBody @Valid BaseBankAddForm addForm) {
        return baseBankService.add(addForm);
    }

    @Operation(summary = "添加客商账号 @author 赵嘉伟")
    @PostMapping("/baseBank/addCust")
    public ResponseDTO<BaseBankVO> addCust(@RequestBody @Valid BaseBankAddForm addForm) {
        return baseBankService.addCust(addForm);
    }

    @Operation(summary = "更新 @author 赵嘉伟")
    @PostMapping("/baseBank/update")
    public ResponseDTO<String> update(@RequestBody @Valid BaseBankUpdateForm updateForm) {
        return baseBankService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author 赵嘉伟")
    @PostMapping("/baseBank/batchDelete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return baseBankService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author 赵嘉伟")
    @GetMapping("/baseBank/delete/{bankId}")
    public ResponseDTO<String> delete(@PathVariable Long bankId) {
        return baseBankService.delete(bankId);
    }
}
