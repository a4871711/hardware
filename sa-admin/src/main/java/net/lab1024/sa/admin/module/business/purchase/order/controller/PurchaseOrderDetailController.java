package net.lab1024.sa.admin.module.business.purchase.order.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.purchase.order.domain.form.PurchaseOrderDetailAddForm;
import net.lab1024.sa.admin.module.business.purchase.order.domain.form.PurchaseOrderDetailQueryForm;
import net.lab1024.sa.admin.module.business.purchase.order.domain.form.PurchaseOrderDetailUpdateForm;
import net.lab1024.sa.admin.module.business.purchase.order.domain.vo.PurchaseOrderDetailVO;
import net.lab1024.sa.admin.module.business.purchase.order.service.PurchaseOrderDetailService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.ValidateList;
import org.springframework.web.bind.annotation.*;

/**
 * 采购订货单详情 Controller
 *
 * @Author 赵嘉伟
 * @Date 2025-07-08 22:10:24
 * @Copyright 赵嘉伟
 */

@RestController
@Tag(name = "采购订货单详情")
public class PurchaseOrderDetailController {

    @Resource
    private PurchaseOrderDetailService purchaseOrderDetailService;

    @Operation(summary = "分页查询 @author 赵嘉伟")
    @PostMapping("/purchaseOrderDetail/queryPage")
    public ResponseDTO<PageResult<PurchaseOrderDetailVO>> queryPage(@RequestBody @Valid PurchaseOrderDetailQueryForm queryForm) {
        return ResponseDTO.ok(purchaseOrderDetailService.queryPage(queryForm));
    }

    @Operation(summary = "选择查询 @author 赵嘉伟")
    @PostMapping("/purchaseOrderDetail/selectPage")
    public ResponseDTO<PageResult<PurchaseOrderDetailVO>> selectQueryPage(@RequestBody @Valid PurchaseOrderDetailQueryForm queryForm) {
        return ResponseDTO.ok(purchaseOrderDetailService.selectQueryPage(queryForm));
    }

    @Operation(summary = "添加 @author 赵嘉伟")
    @PostMapping("/purchaseOrderDetail/add")
    public ResponseDTO<String> add(@RequestBody @Valid PurchaseOrderDetailAddForm addForm) {
        return purchaseOrderDetailService.add(addForm);
    }

    @Operation(summary = "更新 @author 赵嘉伟")
    @PostMapping("/purchaseOrderDetail/update")
    @SaCheckPermission("purchaseOrderDetail:update")
    public ResponseDTO<String> update(@RequestBody @Valid PurchaseOrderDetailUpdateForm updateForm) {
        return purchaseOrderDetailService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author 赵嘉伟")
    @PostMapping("/purchaseOrderDetail/batchDelete")
    @SaCheckPermission("purchaseOrderDetail:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return purchaseOrderDetailService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author 赵嘉伟")
    @GetMapping("/purchaseOrderDetail/delete/{purchaseOrderDetailId}")
    @SaCheckPermission("purchaseOrderDetail:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long purchaseOrderDetailId) {
        return purchaseOrderDetailService.delete(purchaseOrderDetailId);
    }
}
