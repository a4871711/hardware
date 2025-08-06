package net.lab1024.sa.admin.module.system.company.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.system.company.dao.CompanyDao;
import net.lab1024.sa.admin.module.system.company.domain.entity.CompanyBaseEntity;
import net.lab1024.sa.admin.module.system.company.domain.form.CompanyAddForm;
import net.lab1024.sa.admin.module.system.company.domain.form.CompanyUpdateForm;
import net.lab1024.sa.admin.module.system.company.domain.vo.CompanyVO;
import net.lab1024.sa.admin.module.system.department.domain.entity.DepartmentBaseEntity;
import net.lab1024.sa.admin.module.system.department.service.DepartmentService;
import net.lab1024.sa.admin.module.system.employee.domain.form.EmployeeAddForm;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.admin.module.system.invitationcode.domain.entity.CompanyInvitationCodeEntity;
import net.lab1024.sa.admin.module.system.invitationcode.manager.CompanyInvitationCodeManager;
import net.lab1024.sa.admin.module.system.menu.dao.MenuDao;
import net.lab1024.sa.admin.module.system.role.domain.entity.RoleBaseEntity;
import net.lab1024.sa.admin.module.system.role.domain.form.RoleMenuUpdateForm;
import net.lab1024.sa.admin.module.system.role.service.RoleService;
import net.lab1024.sa.admin.module.system.role.service.RoleSubscriptionPlanMenuService;
import net.lab1024.sa.admin.module.system.subscriptionplanmenu.domain.entity.SubscriptionPlanMenuBaseEntity;
import net.lab1024.sa.admin.module.system.subscriptionplanmenu.service.SubscriptionPlanMenuService;
import net.lab1024.sa.base.common.code.SystemErrorCode;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.exception.BusinessException;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartRequestUtil;
import net.lab1024.sa.base.module.support.file.domain.entity.FileBaseEntity;
import net.lab1024.sa.base.module.support.file.service.FileService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * 公司资料 Service
 *
 * @Author 赵嘉伟
 * @Date 2025-03-05 18:27:44
 * @Copyright 赵嘉伟
 */

@Slf4j
@Service
public class CompanyService {

    @Resource
    private CompanyDao companyDao;

    @Resource
    private FileService fileService;

    @Resource
    private CompanyInvitationCodeManager companyInvitationCodeManager;

    @Resource
    private RoleService roleService;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private EmployeeService employeeService;

    @Resource
    private SubscriptionPlanMenuService subscriptionPlanMenuService;

    @Resource
    private MenuDao menuDao;

    @Resource
    private RoleSubscriptionPlanMenuService roleSubscriptionPlanMenuService;


    /**
     * 校验公司代码：检查传入的公司代码是否已存在，若存在则返回错误。
     * 校验邀请码：验证邀请码是否有效且未被使用，若无效或已使用则返回错误。
     * 新增公司信息：将表单数据转换为公司实体，设置订阅相关信息并插入数据库。
     * 更新邀请码：将新增公司的 ID 关联到邀请码实体并更新。
     * 更新文件归属：将公司相关的文件（如logo、证件等）的公司 ID 更新为新增公司的 ID。
     * 初始化角色和部门：
     *     创建默认角色（admin）并关联公司。
     *     创建默认部门并关联公司。
     * 新增员工：创建员工并关联到默认角色和部门，若失败则抛出异常。
     * 配置菜单权限：根据订阅计划获取对应的菜单权限，并为角色分配这些权限。
     * 返回结果：操作成功后返回成功响应。
     * @param addForm addForm
     * @return
     */
    @Transactional(rollbackFor = Throwable.class)
    public ResponseDTO<String> add(CompanyAddForm addForm) {
        // 根据公司编码校验公司信息是否已经存在
        if (findByCompanyCode(addForm.getCompanyCode()) != null) {
            return ResponseDTO.userErrorParam("公司代码已经存在");
        }
        //判断输入的邀请码是否已经有对应的公司id了，如果有，则不允许重复添加
        LambdaQueryWrapper<CompanyInvitationCodeEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CompanyInvitationCodeEntity::getInvitationCode, addForm.getInvitationCode());
        CompanyInvitationCodeEntity companyInvitationCodeEntity = companyInvitationCodeManager.getOne(wrapper);
        if (companyInvitationCodeEntity == null || companyInvitationCodeEntity.getCompanyId() != null) {
            return ResponseDTO.userErrorParam("该邀请码不存在或已经被使用");
        }

        // 新增公司
        CompanyBaseEntity companyEntity = SmartBeanUtil.copy(addForm, CompanyBaseEntity.class);
        companyEntity.setSubscriptionStartDate(companyInvitationCodeEntity.getSubscriptionStartDate());
        companyEntity.setSubscriptionEndDate(companyInvitationCodeEntity.getSubscriptionEndDate());
        companyEntity.setSubscriptionPlan(companyInvitationCodeEntity.getSubscriptionPlan());
        companyDao.insert(companyEntity);

        //更新邀请码的companyId
        companyInvitationCodeEntity.setCompanyId(companyEntity.getCompanyId());
        companyInvitationCodeManager.updateById(companyInvitationCodeEntity);

        //更新 文件 的companyId
        List<Long> fileIds = Stream.of(companyEntity.getCompanyLogoId(),
                        companyEntity.getBusinessLicenseId(),
                        companyEntity.getIdCardBackId(),
                        companyEntity.getIdCardFrontId())
                .filter(Objects::nonNull)
                .toList();
        if(!fileIds.isEmpty()) {
            LambdaUpdateWrapper<FileBaseEntity> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(FileBaseEntity::getCompanyId, companyEntity.getCompanyId());
            updateWrapper.in(FileBaseEntity::getFileId, fileIds);
            fileService.update(updateWrapper);
        }

        // 新建员工,新建角色、新建部门、让员工属于这个角色和这个部门，新建对应的菜单
        RoleBaseEntity roleEntity = new RoleBaseEntity();
        roleEntity.setRoleCode("admin");
        roleEntity.setRoleName("默认角色");
        roleEntity.setCompanyId(companyEntity.getCompanyId());
        roleService.insertRoleMenu(roleEntity);

        DepartmentBaseEntity departmentEntity = new DepartmentBaseEntity();
        departmentEntity.setName("默认部门");
        departmentEntity.setParentId(0L);
        departmentEntity.setSort(0);
        departmentEntity.setCompanyId(companyEntity.getCompanyId());
        departmentService.insert(departmentEntity);

        // 新建一个用户，让用户属于这个角色和部门
        EmployeeAddForm employeeAddForm = getEmployeeAddForm(departmentEntity, roleEntity, companyEntity);
        ResponseDTO<String> resp = employeeService.addEmployee(employeeAddForm);
        if( ! resp.getOk()) {
            throw new BusinessException(resp.getMsg());
        }

        //根据订阅计划 来 获取该公司对应的一些菜单权限
        Integer subscriptionPlan = companyEntity.getSubscriptionPlan();
        List<SubscriptionPlanMenuBaseEntity> subscriptionPlanMenuEntities = subscriptionPlanMenuService.getBySubscriptionPlan(subscriptionPlan);
        List<Long> menuIdList = subscriptionPlanMenuEntities.stream().map(SubscriptionPlanMenuBaseEntity::getMenuId).toList();

        RoleMenuUpdateForm roleMenuUpdateForm = new RoleMenuUpdateForm();
        roleMenuUpdateForm.setRoleId(roleEntity.getRoleId());
        roleMenuUpdateForm.setMenuIdList(menuIdList);
        roleSubscriptionPlanMenuService.updateRoleMenu(roleMenuUpdateForm);
        return ResponseDTO.ok();
    }

    @NotNull
    private static EmployeeAddForm getEmployeeAddForm(DepartmentBaseEntity departmentEntity, RoleBaseEntity roleEntity, CompanyBaseEntity companyEntity) {
        EmployeeAddForm employeeAddForm = new EmployeeAddForm();
        employeeAddForm.setActualName("admin");
        employeeAddForm.setDepartmentId(departmentEntity.getDepartmentId());
        List<Long> roleIdList = new ArrayList<>();
        roleIdList.add(roleEntity.getRoleId());
        employeeAddForm.setRoleIdList(roleIdList);
        employeeAddForm.setLoginName("admin");
        employeeAddForm.setCompanyId(companyEntity.getCompanyId());
        employeeAddForm.setGender(1);
        return employeeAddForm;
    }

    /**
     * 更新
     *
     * @param updateForm
     * @return
     */
    public ResponseDTO<String> update(CompanyUpdateForm updateForm) {
        CompanyBaseEntity companyEntity = SmartBeanUtil.copy(updateForm, CompanyBaseEntity.class);
        if (companyDao.updateById(companyEntity) > 0) {
            return ResponseDTO.ok();
        } else {
            return ResponseDTO.error(SystemErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 查询当前公司的详细信息
     * 
     * @return 包含公司详细信息的 ResponseDTO 对象
     */
    public ResponseDTO<CompanyVO> query() {
        Long companyId = SmartRequestUtil.getRequestCompanyId();
        CompanyBaseEntity companyEntity = companyDao.selectById(companyId);
        CompanyVO result = SmartBeanUtil.copy(companyEntity, CompanyVO.class);
        // 获取logoId，营业执照id，身份证正面和背面id
        Long companyLogoId = result.getCompanyLogoId();
        Long businessLicenseId = result.getBusinessLicenseId();
        Long idCardFrontId = result.getIdCardFrontId();
        Long idCardBackId = result.getIdCardBackId();

        // 处理可能的 null 值
         result.setLogoFileList(fileService.getFileByIds(companyLogoId != null ? List.of(companyLogoId) : List.of()));
        result.setLicenseFileList(fileService.getFileByIds(businessLicenseId != null ? List.of(businessLicenseId) : List.of()));
        result.setIdCardFrontFileList(fileService.getFileByIds(idCardFrontId != null ? List.of(idCardFrontId) : List.of()));
        result.setIdCardBackFileList(fileService.getFileByIds(idCardBackId != null ? List.of(idCardBackId) : List.of()));

        return ResponseDTO.ok(result);
    }

    public CompanyBaseEntity findByCompanyCode(String companyCode) {
        return companyDao.selectOne(
                new LambdaQueryWrapper<CompanyBaseEntity>().eq(CompanyBaseEntity::getCompanyCode, companyCode)
        );
    }
}
