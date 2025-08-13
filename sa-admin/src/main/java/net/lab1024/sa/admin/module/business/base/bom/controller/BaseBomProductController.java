package net.lab1024.sa.admin.module.business.base.bom.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.base.bom.domain.form.BaseBomProductAddForm;
import net.lab1024.sa.admin.module.business.base.bom.domain.form.BaseBomProductQueryForm;
import net.lab1024.sa.admin.module.business.base.bom.domain.form.BaseBomProductUpdateForm;
import net.lab1024.sa.admin.module.business.base.bom.domain.vo.BaseBomProductVO;
import net.lab1024.sa.admin.module.business.base.bom.service.BaseBomProductService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.ValidateList;
import org.springframework.web.bind.annotation.*;

/**
 * 多级BOM-产品材料 Controller
 *
 * @Author 赵嘉伟
 * @Date 2025-04-30 21:05:57
 * @Copyright 赵嘉伟
 */

@RestController
@Tag(name = "多级BOM-产品材料")
@RequestMapping("/baseBomProduct")
public class BaseBomProductController {

    @Resource
    private BaseBomProductService baseBomProductService;

    @Operation(summary = "分页查询 @author 赵嘉伟")
    @PostMapping("/queryPage")
    @SaCheckPermission("baseBomProduct:query")
    public ResponseDTO<PageResult<BaseBomProductVO>> queryPage(@RequestBody @Valid BaseBomProductQueryForm queryForm) {
        return ResponseDTO.ok(baseBomProductService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author 赵嘉伟")
    @PostMapping("/add")
    @SaCheckPermission("baseBomProduct:add")
    public ResponseDTO<String> add(@RequestBody @Valid BaseBomProductAddForm addForm) {
        return baseBomProductService.add(addForm);
    }

    @Operation(summary = "更新 @author 赵嘉伟")
    @PutMapping("/update")
    @SaCheckPermission("baseBomProduct:update")
    public ResponseDTO<String> update(@RequestBody @Valid BaseBomProductUpdateForm updateForm) {
        return baseBomProductService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author 赵嘉伟")
    @DeleteMapping("/batchDelete")
    @SaCheckPermission("baseBomProduct:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return baseBomProductService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author 赵嘉伟")
    @DeleteMapping("/delete/{bomProductId}")
    @SaCheckPermission("baseBomProduct:delete")
    public ResponseDTO<String> delete(@PathVariable Long bomProductId) {
        return baseBomProductService.delete(bomProductId);
    }
}
