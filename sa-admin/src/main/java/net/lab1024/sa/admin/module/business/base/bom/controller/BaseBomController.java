package net.lab1024.sa.admin.module.business.base.bom.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.base.bom.domain.form.*;
import net.lab1024.sa.admin.module.business.base.bom.domain.vo.BaseBomDetailVO;
import net.lab1024.sa.admin.module.business.base.bom.domain.vo.BaseBomProductVO;
import net.lab1024.sa.admin.module.business.base.bom.domain.vo.BaseBomVO;
import net.lab1024.sa.admin.module.business.base.bom.service.BaseBomService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 多级BOM-主表 Controller
 *
 * @Author 赵嘉伟
 * @Date 2025-04-25 23:33:04
 * @Copyright 赵嘉伟
 */

@RestController
@Tag(name = "多级BOM-主表")
public class BaseBomController {

    @Resource
    private BaseBomService baseBomService;

    @Operation(summary = "分页查询 @author 赵嘉伟")
    @PostMapping("/baseBom/queryPage")
    @SaCheckPermission("baseBom:query")
    public ResponseDTO<PageResult<BaseBomVO>> queryPage(@RequestBody @Valid BaseBomQueryForm queryForm) {
        return ResponseDTO.ok(baseBomService.queryPage(queryForm));
    }

    @Operation(summary = "search分页查询 @author 赵嘉伟")
    @PostMapping("/baseBom/querySearch")
    public ResponseDTO<PageResult<BaseBomVO>> querySearch(@RequestBody @Valid BaseBomQueryForm queryForm) {
        return ResponseDTO.ok(baseBomService.querySearch(queryForm));
    }

    @Operation(summary = "添加 @author 赵嘉伟")
    @PostMapping("/baseBom/add")
    @SaCheckPermission("baseBom:add")
    public ResponseDTO<String> add(@RequestBody @Valid BaseBomAddForm addForm) {
        return baseBomService.add(addForm);
    }

    @Operation(summary = "更新 @author 赵嘉伟")
    @PostMapping("/baseBom/saveOrUpdate")
    public ResponseDTO<Long> saveOrUpdate(@RequestBody @Valid BaseBomUpdateForm updateForm) {
        return baseBomService.saveOrUpdate(updateForm);
    }

    @Operation(summary = "详情 @author 赵嘉伟")
    @GetMapping("/baseBom/detail/{bomId}")
    public ResponseDTO<BaseBomDetailVO> detail(@PathVariable Long bomId) {
        return baseBomService.detail(bomId);
    }

    @Operation(summary = "根据BomIdList获取产品材料 @author 赵嘉伟")
    @PostMapping("/baseBom/getByBomIdList")
    public ResponseDTO<List<BaseBomProductVO>> getByBomIdList(@RequestBody List<Long> bomIdList) {
        return baseBomService.getByBomIdList(bomIdList);
    }


    @Operation(summary = "单个删除 @author 赵嘉伟")
    @GetMapping("/baseBom/delete/{bomId}")
    @SaCheckPermission("baseBom:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long bomId) {
        return baseBomService.delete(bomId);
    }

    @Operation(summary = "审核/反审核 @author 赵嘉伟")
    @PostMapping("/baseBom/audit")
    public ResponseDTO<String> audit(@Valid @RequestBody BaseBomAuditForm baseBomAuditForm) {
        return baseBomService.audit(baseBomAuditForm);
    }

    @Operation(summary = "作废/反作废 @author 赵嘉伟")
    @PostMapping("/baseBom/invalid")
    @SaCheckPermission("baseBom:invalid")
    public ResponseDTO<String> invalid(@Valid @RequestBody BaseBomInvalidForm baseBomInvalidForm) {
        return baseBomService.invalid(baseBomInvalidForm);
    }


}
