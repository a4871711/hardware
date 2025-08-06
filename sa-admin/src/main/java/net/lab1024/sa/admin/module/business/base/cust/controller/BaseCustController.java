package net.lab1024.sa.admin.module.business.base.cust.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.base.cust.domain.form.BaseCustAddForm;
import net.lab1024.sa.admin.module.business.base.cust.domain.form.BaseCustQueryForm;
import net.lab1024.sa.admin.module.business.base.cust.domain.form.BaseCustUpdateForm;
import net.lab1024.sa.admin.module.business.base.cust.domain.vo.BaseCustDetailVO;
import net.lab1024.sa.admin.module.business.base.cust.domain.vo.BaseCustVO;
import net.lab1024.sa.admin.module.business.base.cust.service.BaseCustService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.ValidateList;
import org.springframework.web.bind.annotation.*;

/**
 * 客商资料 Controller
 *
 * @Author 赵嘉伟
 * @Date 2025-03-27 23:10:41
 */

@RestController
@Tag(name = "客商资料")
public class BaseCustController {

    @Resource
    private BaseCustService baseCustService;


    @Operation(summary = "分页查询 @author 赵嘉伟")
    @PostMapping("/baseCust/queryPage")
    public ResponseDTO<PageResult<BaseCustVO>> queryPage(@RequestBody @Valid BaseCustQueryForm queryForm) {
        return ResponseDTO.ok(baseCustService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author 赵嘉伟")
    @PostMapping("/baseCust/add")
    public ResponseDTO<String> add(@RequestBody @Valid BaseCustAddForm addForm) {
        return baseCustService.add(addForm);
    }

    @Operation(summary = "更新 @author 赵嘉伟")
    @PostMapping("/baseCust/update")
    public ResponseDTO<String> update(@RequestBody @Valid BaseCustUpdateForm updateForm) {
        return baseCustService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author 赵嘉伟")
    @PostMapping("/baseCust/batchDelete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return baseCustService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author 赵嘉伟")
    @GetMapping("/baseCust/delete/{baseCustId}")
    public ResponseDTO<String> batchDelete(@PathVariable Long baseCustId) {
        return baseCustService.delete(baseCustId);
    }

    @Operation(summary = "根据 custId 查询客商详情 @author 赵嘉伟")
    @GetMapping("/baseCust/detail/{custId}")
    public ResponseDTO<BaseCustDetailVO> getDetailById(@PathVariable Long custId) {
        return baseCustService.getDetailById(custId);
    }
}
