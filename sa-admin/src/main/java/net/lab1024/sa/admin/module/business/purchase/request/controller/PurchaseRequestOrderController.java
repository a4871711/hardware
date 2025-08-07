package net.lab1024.sa.admin.module.business.purchase.request.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.purchase.request.domain.form.*;
import net.lab1024.sa.admin.module.business.purchase.request.domain.vo.PurchaseRequestOrderDetailDataVO;
import net.lab1024.sa.admin.module.business.purchase.request.domain.vo.PurchaseRequestOrderVO;
import net.lab1024.sa.admin.module.business.purchase.request.domain.vo.PurchaseRequestOrderDetailInfoVO;
import net.lab1024.sa.admin.module.business.purchase.request.domain.vo.PurchaseRequestOrderDetailVO;
import net.lab1024.sa.admin.module.business.purchase.request.service.PurchaseRequestOrderService;
import net.lab1024.sa.admin.module.business.purchase.request.service.PurchaseRequestOrderDetailService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.ValidateList;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 采购申请单 Controller
 *
 * @Author 赵嘉伟
 * @Date 2025-06-23 22:34:10
 * @Copyright 赵嘉伟
 */

@RestController
@Tag(name = "采购申请单")
public class PurchaseRequestOrderController {

    @Resource
    private PurchaseRequestOrderService purchaseRequestOrderService;

    @Resource
    private PurchaseRequestOrderDetailService purchaseRequestOrderDetailService;

    @Operation(summary = "分页查询 @author 赵嘉伟")
    @PostMapping("/purchaseRequestOrder/queryPage")
    @SaCheckPermission("purchaseRequestOrder:query")
    public ResponseDTO<PageResult<PurchaseRequestOrderVO>> queryPage(@RequestBody @Valid PurchaseRequestOrderQueryForm queryForm) {
        return ResponseDTO.ok(purchaseRequestOrderService.queryPage(queryForm));
    }

    @Operation(summary = "采购申请单明细分页查询（未被已审核采购订货单引用）")
    @PostMapping("/purchaseRequestOrder/detail/queryPage")
    public ResponseDTO<PageResult<PurchaseRequestOrderDetailInfoVO>> queryDetailPage(@RequestBody @Valid PurchaseRequestOrderQueryForm queryForm) {
        return ResponseDTO.ok(purchaseRequestOrderDetailService.queryDetailPage(queryForm));
    }

    @Operation(summary = "根据明细ID集合查询")
    @PostMapping("/purchaseRequestOrder/detail/queryByIdList")
    public ResponseDTO<List<PurchaseRequestOrderDetailVO>> queryDetailByIdList(@RequestBody ValidateList<Long> idList) {
        return ResponseDTO.ok(purchaseRequestOrderDetailService.selectByIdList(idList));
    }

    @Operation(summary = "添加 @author 赵嘉伟")
    @PostMapping("/purchaseRequestOrder/add")
    @SaCheckPermission("purchaseRequestOrder:add")
    public ResponseDTO<String> add(@RequestBody @Valid PurchaseRequestOrderAddForm addForm) {
        return purchaseRequestOrderService.add(addForm);
    }

    @Operation(summary = "保存或更新 @author 赵嘉伟")
    @PostMapping("/purchaseRequestOrder/saveOrUpdate")
    public ResponseDTO<Long> saveOrUpdate(@RequestBody @Valid PurchaseRequestOrderAddForm addForm) {
        return purchaseRequestOrderService.saveOrUpdate(addForm);
    }

    @Operation(summary = "查询 @author 赵嘉伟")
    @GetMapping("/purchaseRequestOrder/detail/{purchaseRequestOrderId}")
    public ResponseDTO<PurchaseRequestOrderDetailDataVO> detail(@PathVariable Long purchaseRequestOrderId) {
        return ResponseDTO.ok(purchaseRequestOrderService.detail(purchaseRequestOrderId));
    }



    @Operation(summary = "更新 @author 赵嘉伟")
    @PostMapping("/purchaseRequestOrder/update")
    @SaCheckPermission("purchaseRequestOrder:update")
    public ResponseDTO<String> update(@RequestBody @Valid PurchaseRequestOrderUpdateForm updateForm) {
        return purchaseRequestOrderService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author 赵嘉伟")
    @PostMapping("/purchaseRequestOrder/batchDelete")
    @SaCheckPermission("purchaseRequestOrder:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return purchaseRequestOrderService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author 赵嘉伟")
    @GetMapping("/purchaseRequestOrder/delete/{purchaseRequestOrderId}")
    @SaCheckPermission("purchaseRequestOrder:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long purchaseRequestOrderId) {
        return purchaseRequestOrderService.delete(purchaseRequestOrderId);
    }

    @Operation(summary = "审核/反审核 @author 赵嘉伟")
    @PostMapping("/purchaseRequestOrder/changedStatus")
    public ResponseDTO<String> changedStatus(@Valid @RequestBody PurchaseRequestOrderChangedStatusForm purchaseRequestOrderChangedStatusForm) {
        return purchaseRequestOrderService.changedStatus(purchaseRequestOrderChangedStatusForm);
    }

    @Operation(summary = "作废/反作废 @author 赵嘉伟")
    @PostMapping("/purchaseRequestOrder/invalid")
    public ResponseDTO<String> invalid(@Valid @RequestBody PurchaseRequestOrderAddForm purchaseRequestOrderAddForm) {
        return purchaseRequestOrderService.invalid(purchaseRequestOrderAddForm);
    }


    @Operation(summary = "判断是否有对应的采购订货单引用了销售订货单 @author 赵嘉伟")
    @PostMapping("/purchaseRequestOrder/isReferenced")
    public ResponseDTO<List<PurchaseRequestOrderVO>> isReferenced(@Valid @RequestBody PurchaseRequestOrderReferenceForm purchaseRequestOrderReferenceForm) {
        return purchaseRequestOrderService.isReferenced(purchaseRequestOrderReferenceForm);
    }
}
