package net.lab1024.sa.admin.module.business.sales.order.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderRequirementsAddForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderRequirementsQueryForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderRequirementsUpdateForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.vo.SalesOrderRequirementsVO;
import net.lab1024.sa.admin.module.business.sales.order.service.SalesOrderRequirementsService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.ValidateList;
import org.springframework.web.bind.annotation.*;

/**
 * 销售订单-订单要求表 Controller
 *
 * @Author 赵嘉伟
 * @Date 2025-05-25 19:36:51
 * @Copyright 赵嘉伟
 */

@RestController
@Tag(name = "销售订单-订单要求表")
public class SalesOrderRequirementsController {

    @Resource
    private SalesOrderRequirementsService salesOrderRequirementsService;

    @Operation(summary = "分页查询 @author 赵嘉伟")
    @PostMapping("/salesOrderRequirements/queryPage")
    @SaCheckPermission("salesOrderRequirements:query")
    public ResponseDTO<PageResult<SalesOrderRequirementsVO>> queryPage(@RequestBody @Valid SalesOrderRequirementsQueryForm queryForm) {
        return ResponseDTO.ok(salesOrderRequirementsService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author 赵嘉伟")
    @PostMapping("/salesOrderRequirements/add")
    @SaCheckPermission("salesOrderRequirements:add")
    public ResponseDTO<String> add(@RequestBody @Valid SalesOrderRequirementsAddForm addForm) {
        return salesOrderRequirementsService.add(addForm);
    }

    @Operation(summary = "更新 @author 赵嘉伟")
    @PostMapping("/salesOrderRequirements/update")
    @SaCheckPermission("salesOrderRequirements:update")
    public ResponseDTO<String> update(@RequestBody @Valid SalesOrderRequirementsUpdateForm updateForm) {
        return salesOrderRequirementsService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author 赵嘉伟")
    @PostMapping("/salesOrderRequirements/batchDelete")
    @SaCheckPermission("salesOrderRequirements:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return salesOrderRequirementsService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author 赵嘉伟")
    @GetMapping("/salesOrderRequirements/delete/{salesOrderRequirementsId}")
    @SaCheckPermission("salesOrderRequirements:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long salesOrderRequirementsId) {
        return salesOrderRequirementsService.delete(salesOrderRequirementsId);
    }
}
