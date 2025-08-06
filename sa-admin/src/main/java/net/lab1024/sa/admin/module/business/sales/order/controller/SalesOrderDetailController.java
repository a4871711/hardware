package net.lab1024.sa.admin.module.business.sales.order.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderDetailAddForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderDetailQueryForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderDetailUpdateForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.vo.SalesOrderDetailVO;
import net.lab1024.sa.admin.module.business.sales.order.service.SalesOrderDetailService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.ValidateList;
import org.springframework.web.bind.annotation.*;

/**
 * 销售订货单明细表 Controller
 *
 * @Author 赵嘉伟
 * @Date 2025-05-25 19:36:41
 * @Copyright 赵嘉伟
 */

@RestController
@Tag(name = "销售订货单明细表")
public class SalesOrderDetailController {

    @Resource
    private SalesOrderDetailService salesOrderDetailService;

    @Operation(summary = "分页查询 @author 赵嘉伟")
    @PostMapping("/salesOrderDetail/queryPage")
    @SaCheckPermission("salesOrderDetail:query")
    public ResponseDTO<PageResult<SalesOrderDetailVO>> queryPage(@RequestBody @Valid SalesOrderDetailQueryForm queryForm) {
        return ResponseDTO.ok(salesOrderDetailService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author 赵嘉伟")
    @PostMapping("/salesOrderDetail/add")
    @SaCheckPermission("salesOrderDetail:add")
    public ResponseDTO<String> add(@RequestBody @Valid SalesOrderDetailAddForm addForm) {
        return salesOrderDetailService.add(addForm);
    }

    @Operation(summary = "更新 @author 赵嘉伟")
    @PostMapping("/salesOrderDetail/update")
    @SaCheckPermission("salesOrderDetail:update")
    public ResponseDTO<String> update(@RequestBody @Valid SalesOrderDetailUpdateForm updateForm) {
        return salesOrderDetailService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author 赵嘉伟")
    @PostMapping("/salesOrderDetail/batchDelete")
    @SaCheckPermission("salesOrderDetail:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return salesOrderDetailService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author 赵嘉伟")
    @GetMapping("/salesOrderDetail/delete/{salesOrderDetailId}")
    @SaCheckPermission("salesOrderDetail:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long salesOrderDetailId) {
        return salesOrderDetailService.delete(salesOrderDetailId);
    }
}
