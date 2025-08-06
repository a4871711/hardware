package net.lab1024.sa.admin.module.business.purchase.order.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.purchase.order.domain.form.PurchaseOrderAddForm;
import net.lab1024.sa.admin.module.business.purchase.order.domain.form.PurchaseOrderAuditForm;
import net.lab1024.sa.admin.module.business.purchase.order.domain.form.PurchaseOrderInvalidForm;
import net.lab1024.sa.admin.module.business.purchase.order.domain.form.PurchaseOrderQueryForm;
import net.lab1024.sa.admin.module.business.purchase.order.domain.vo.PurchaseOrderVO;
import net.lab1024.sa.admin.module.business.purchase.order.service.PurchaseOrderService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.ValidateList;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 采购订货单 Controller
 *
 * @Author 赵嘉伟
 * @Date 2025-07-08 22:10:23
 * @Copyright 赵嘉伟
 */

@RestController
@Tag(name = "采购订货单")
public class PurchaseOrderController {

    @Resource
    private PurchaseOrderService purchaseOrderService;

    @Operation(summary = "分页查询 @author 赵嘉伟")
    @PostMapping("/purchaseOrder/queryPage")
    @SaCheckPermission("purchaseOrder:query")
    public ResponseDTO<PageResult<PurchaseOrderVO>> queryPage(@RequestBody @Valid PurchaseOrderQueryForm queryForm) {
        return ResponseDTO.ok(purchaseOrderService.queryPage(queryForm));
    }

    @Operation(summary = "保存或更新 @author 赵嘉伟")
    @PostMapping("/purchaseOrder/saveOrUpdate")
    public ResponseDTO<Long> saveOrUpdate(@RequestBody @Valid PurchaseOrderAddForm addForm) {
        return purchaseOrderService.saveOrUpdate(addForm);
    }

    @Operation(summary = "审核/反审核 @author 赵嘉伟")
    @PostMapping("/purchaseOrder/audit")
    public ResponseDTO<String> audit(@Valid @RequestBody PurchaseOrderAuditForm purchaseOrderAuditForm) {
        return purchaseOrderService.audit(purchaseOrderAuditForm);
    }

    @Operation(summary = "作废/反作废 @author 赵嘉伟")
    @PostMapping("/purchaseOrder/invalid")
    public ResponseDTO<String> invalid(@Valid @RequestBody PurchaseOrderInvalidForm purchaseOrderInvalidForm) {
        return purchaseOrderService.invalid(purchaseOrderInvalidForm);
    }

    @Operation(summary = "单个删除 @author 赵嘉伟")
    @GetMapping("/purchaseOrder/delete/{purchaseOrderId}")
    @SaCheckPermission("purchaseOrder:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long purchaseOrderId) {
        return purchaseOrderService.delete(purchaseOrderId);
    }

    @Operation(summary = "判断采购申请单是否被未审核采购订货单引用")
    @PostMapping("/purchaseOrder/isReferenced")
    public ResponseDTO<List<PurchaseOrderVO>> isReferenced(@Valid @RequestBody ValidateList<Long> idList) {
        return purchaseOrderService.isReferenced(idList);
    }
}
