package net.lab1024.sa.admin.module.business.purchase.request.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.purchase.request.domain.form.PurchaseRequestOrderDetailAddForm;
import net.lab1024.sa.admin.module.business.purchase.request.domain.form.PurchaseRequestOrderDetailQueryForm;
import net.lab1024.sa.admin.module.business.purchase.request.domain.form.PurchaseRequestOrderDetailUpdateForm;
import net.lab1024.sa.admin.module.business.purchase.request.domain.vo.PurchaseRequestOrderDetailVO;
import net.lab1024.sa.admin.module.business.purchase.request.service.PurchaseRequestOrderDetailService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.ValidateList;
import org.springframework.web.bind.annotation.*;

/**
 * 采购申请单详情 Controller
 *
 * @Author 赵嘉伟
 * @Date 2025-06-23 22:34:08
 * @Copyright 赵嘉伟
 */

@RestController
@Tag(name = "采购申请单详情")
public class PurchaseRequestOrderDetailController {

    @Resource
    private PurchaseRequestOrderDetailService purchaseRequestOrderDetailService;

    @Operation(summary = "分页查询 @author 赵嘉伟")
    @PostMapping("/purchaseRequestOrderDetail/queryPage")
    @SaCheckPermission("purchaseRequestOrderDetail:query")
    public ResponseDTO<PageResult<PurchaseRequestOrderDetailVO>> queryPage(@RequestBody @Valid PurchaseRequestOrderDetailQueryForm queryForm) {
        return ResponseDTO.ok(purchaseRequestOrderDetailService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author 赵嘉伟")
    @PostMapping("/purchaseRequestOrderDetail/add")
    @SaCheckPermission("purchaseRequestOrderDetail:add")
    public ResponseDTO<String> add(@RequestBody @Valid PurchaseRequestOrderDetailAddForm addForm) {
        return purchaseRequestOrderDetailService.add(addForm);
    }

    @Operation(summary = "更新 @author 赵嘉伟")
    @PostMapping("/purchaseRequestOrderDetail/update")
    @SaCheckPermission("purchaseRequestOrderDetail:update")
    public ResponseDTO<String> update(@RequestBody @Valid PurchaseRequestOrderDetailUpdateForm updateForm) {
        return purchaseRequestOrderDetailService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author 赵嘉伟")
    @PostMapping("/purchaseRequestOrderDetail/batchDelete")
    @SaCheckPermission("purchaseRequestOrderDetail:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return purchaseRequestOrderDetailService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author 赵嘉伟")
    @GetMapping("/purchaseRequestOrderDetail/delete/{purchaseRequestOrderDetailId}")
    @SaCheckPermission("purchaseRequestOrderDetail:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long purchaseRequestOrderDetailId) {
        return purchaseRequestOrderDetailService.delete(purchaseRequestOrderDetailId);
    }
}
