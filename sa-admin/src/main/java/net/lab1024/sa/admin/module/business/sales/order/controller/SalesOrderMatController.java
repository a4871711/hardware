package net.lab1024.sa.admin.module.business.sales.order.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderMatAddForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderMatQueryForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderMatUpdateForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.vo.SalesOrderMatVO;
import net.lab1024.sa.admin.module.business.sales.order.service.SalesOrderMatService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.ValidateList;
import org.springframework.web.bind.annotation.*;

/**
 * 销售订单原材料表 Controller
 *
 * @Author 赵嘉伟
 * @Date 2025-05-29 23:32:37
 * @Copyright 赵嘉伟
 */

@RestController
@Tag(name = "销售订单原材料表")
public class SalesOrderMatController {

    @Resource
    private SalesOrderMatService salesOrderMatService;

    @Operation(summary = "分页查询 @author 赵嘉伟")
    @PostMapping("/salesOrderMat/queryPage")
    @SaCheckPermission("salesOrderMat:query")
    public ResponseDTO<PageResult<SalesOrderMatVO>> queryPage(@RequestBody @Valid SalesOrderMatQueryForm queryForm) {
        return ResponseDTO.ok(salesOrderMatService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author 赵嘉伟")
    @PostMapping("/salesOrderMat/add")
    @SaCheckPermission("salesOrderMat:add")
    public ResponseDTO<String> add(@RequestBody @Valid SalesOrderMatAddForm addForm) {
        return salesOrderMatService.add(addForm);
    }

    @Operation(summary = "更新 @author 赵嘉伟")
    @PostMapping("/salesOrderMat/update")
    @SaCheckPermission("salesOrderMat:update")
    public ResponseDTO<String> update(@RequestBody @Valid SalesOrderMatUpdateForm updateForm) {
        return salesOrderMatService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author 赵嘉伟")
    @PostMapping("/salesOrderMat/batchDelete")
    @SaCheckPermission("salesOrderMat:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return salesOrderMatService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author 赵嘉伟")
    @GetMapping("/salesOrderMat/delete/{salesOrderMatId}")
    @SaCheckPermission("salesOrderMat:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long salesOrderMatId) {
        return salesOrderMatService.delete(salesOrderMatId);
    }
}
