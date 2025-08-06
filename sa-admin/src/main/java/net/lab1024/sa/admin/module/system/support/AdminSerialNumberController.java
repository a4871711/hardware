package net.lab1024.sa.admin.module.system.support;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.base.common.controller.SupportBaseController;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartEnumUtil;
import net.lab1024.sa.base.common.util.SmartRequestUtil;
import net.lab1024.sa.base.constant.SwaggerTagConst;
import net.lab1024.sa.base.module.support.serialnumber.constant.SerialNumberIdEnum;
import net.lab1024.sa.base.module.support.serialnumber.dao.SerialNumberDao;
import net.lab1024.sa.base.module.support.serialnumber.domain.*;
import net.lab1024.sa.base.module.support.serialnumber.domain.vo.SerialNumberEntityVO;
import net.lab1024.sa.base.module.support.serialnumber.service.SerialNumberRecordService;
import net.lab1024.sa.base.module.support.serialnumber.service.SerialNumberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 单据序列号
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-03-25 21:46:07
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Tag(name = SwaggerTagConst.Support.SERIAL_NUMBER)
@RestController
public class AdminSerialNumberController extends SupportBaseController {

    @Resource
    private SerialNumberDao serialNumberDao;

    @Resource
    private SerialNumberService serialNumberService;

    @Resource
    private SerialNumberRecordService serialNumberRecordService;

    @Operation(summary = "生成单号 @author 卓大")
    @PostMapping("/serialNumber/generate")
    @SaCheckPermission("support:serialNumber:generate")
    public ResponseDTO<List<String>> generate(@RequestBody @Valid SerialNumberGenerateForm generateForm) {
        Long companyId = SmartRequestUtil.getRequestCompanyId();
        SerialNumberIdEnum serialNumberIdEnum = SmartEnumUtil.getEnumByValue(generateForm.getSerialNumberId(), SerialNumberIdEnum.class);
        if (null == serialNumberIdEnum) {
            return ResponseDTO.userErrorParam("SerialNumberId，不存在" + generateForm.getSerialNumberId());
        }
        return ResponseDTO.ok(serialNumberService.generate(serialNumberIdEnum, generateForm.getCount(), companyId));
    }

    @Operation(summary = "获取所有单号定义 @author 卓大")
    @GetMapping("/serialNumber/all")
    public ResponseDTO<List<SerialNumberEntity>> getAll() {
        return ResponseDTO.ok(serialNumberDao.getAll(SmartRequestUtil.getRequestCompanyId()));
    }

    @Operation(summary = "修改单号 @author 赵嘉伟")
    @PostMapping("/serialNumber/update")
    public ResponseDTO<String> update(@RequestBody CompanySerialNumberUpdateForm companySerialNumberUpdateForm) {
        return serialNumberService.update(companySerialNumberUpdateForm);
    }

    @Operation(summary = "获取单号 @author 卓大")
    @PostMapping("/serialNumber/getBySerialNumberId/{serialNumberId}")
    public ResponseDTO<SerialNumberEntityVO> getBySerialNumberId(@PathVariable Integer serialNumberId) {
        return serialNumberService.getBySerialNumberId(serialNumberId, SmartRequestUtil.getRequestUser());
    }

    @Operation(summary = "获取生成记录 @author 卓大")
    @PostMapping("/serialNumber/queryRecord")
    @SaCheckPermission("support:serialNumber:record")
    public ResponseDTO<PageResult<SerialNumberRecordEntity>> queryRecord(@RequestBody @Valid SerialNumberRecordQueryForm queryForm) {
        Long companyId = SmartRequestUtil.getRequestCompanyId();
        queryForm.setCompanyId(companyId);
        return ResponseDTO.ok(serialNumberRecordService.query(queryForm));
    }

}
