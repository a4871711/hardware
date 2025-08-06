package net.lab1024.sa.admin.module.business.base.componentdict.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.module.business.base.componentdict.domain.form.ComponentDictAddForm;
import net.lab1024.sa.admin.module.business.base.componentdict.domain.form.ComponentDictQueryForm;
import net.lab1024.sa.admin.module.business.base.componentdict.domain.form.ComponentDictUpdateForm;
import net.lab1024.sa.admin.module.business.base.componentdict.domain.vo.ComponentDictVO;
import net.lab1024.sa.admin.module.business.base.componentdict.service.ComponentDictService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.ValidateList;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系列组件表 Controller
 *
 * @Author 赵嘉伟
 * @Date 2025-05-01 18:07:45
 * @Copyright 赵嘉伟
 */

@RestController
@Tag(name = "系列组件表")
public class ComponentDictController {

    @Resource
    private ComponentDictService componentDictService;

    @Operation(summary = "分页查询 @author 赵嘉伟")
    @PostMapping("/componentDict/queryPage")
    public ResponseDTO<PageResult<ComponentDictVO>> queryPage(@RequestBody @Valid ComponentDictQueryForm queryForm) {
        return ResponseDTO.ok(componentDictService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author 赵嘉伟")
    @PostMapping("/componentDict/add")
    public ResponseDTO<String> add(@RequestBody @Valid ComponentDictAddForm addForm) {
        return componentDictService.add(addForm);
    }

    @Operation(summary = "更新 @author 赵嘉伟")
    @PostMapping("/componentDict/update")
    public ResponseDTO<String> update(@RequestBody @Valid ComponentDictUpdateForm updateForm) {
        return componentDictService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author 赵嘉伟")
    @PostMapping("/componentDict/batchDelete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return componentDictService.batchDelete(idList);
    }


}
