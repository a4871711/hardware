package net.lab1024.sa.admin.module.business.sales.order.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderAddForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.form.SalesOrderQueryForm;
import net.lab1024.sa.admin.module.business.sales.order.domain.vo.SalesOrderDetailDataVO;
import net.lab1024.sa.admin.module.business.sales.order.domain.vo.SalesOrderQuerySearchVO;
import net.lab1024.sa.admin.module.business.sales.order.domain.vo.SalesOrderVO;
import net.lab1024.sa.admin.module.business.sales.order.service.SalesOrderService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.*;

/**
 * 销售订货单 Controller
 *
 * @Author 赵嘉伟
 * @Date 2025-05-25 19:36:32
 * @Copyright 赵嘉伟
 */

@RestController
@Tag(name = "销售订货单")
public class SalesOrderController {

    @Resource
    private SalesOrderService salesOrderService;

    @Operation(summary = "分页查询 @author 赵嘉伟")
    @PostMapping("/salesOrder/queryPage")
    @SaCheckPermission("salesOrder:query")
    public ResponseDTO<PageResult<SalesOrderVO>> queryPage(@RequestBody @Valid SalesOrderQueryForm queryForm) {
        return ResponseDTO.ok(salesOrderService.queryPage(queryForm));
    }

    @Operation(summary = "查询 分页")
    @PostMapping("/salesOrder/querySearch")
    public ResponseDTO<PageResult<SalesOrderQuerySearchVO>> querySearch(@RequestBody @Valid SalesOrderQueryForm queryForm) {
        return ResponseDTO.ok(salesOrderService.querySearch(queryForm));
    }

    @Operation(summary = "查询 @author 赵嘉伟")
    @GetMapping("/salesOrder/detail/{salesOrderId}")
    public ResponseDTO<SalesOrderDetailDataVO> detail(@PathVariable Long salesOrderId) {
        return ResponseDTO.ok(salesOrderService.detail(salesOrderId));
    }


    @Operation(summary = "保存或更新 @author 赵嘉伟")
    @PostMapping("/salesOrder/saveOrUpdate")
    public ResponseDTO<Long> saveOrUpdate(@RequestBody @Valid SalesOrderAddForm addForm) {
        return salesOrderService.saveOrUpdate(addForm);
    }

    @Operation(summary = "单个删除 @author 赵嘉伟")
    @GetMapping("/salesOrder/delete/{salesOrderId}")
    @SaCheckPermission("salesOrder:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long salesOrderId) {
        return salesOrderService.delete(salesOrderId);
    }

    @Operation(summary = "审核/反审核 @author 赵嘉伟")
    @PostMapping("/salesOrder/audit")
    public ResponseDTO<String> audit(@Valid @RequestBody SalesOrderAddForm salesOrderAddForm) {
        return salesOrderService.audit(salesOrderAddForm);
    }

    @Operation(summary = "作废/反作废 @author 赵嘉伟")
    @PostMapping("/salesOrder/invalid")
    public ResponseDTO<String> invalid(@Valid @RequestBody SalesOrderAddForm salesOrderAddForm) {
        return salesOrderService.invalid(salesOrderAddForm);
    }

}
