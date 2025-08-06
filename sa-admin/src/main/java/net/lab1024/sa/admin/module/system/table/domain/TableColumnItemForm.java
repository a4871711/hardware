package net.lab1024.sa.admin.module.system.table.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import net.lab1024.sa.admin.module.system.employee.domain.vo.EmployeeVO;
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleVO;

import java.util.List;

/**
 * 自定义表格列
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-08-12 22:52:21
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class TableColumnItemForm {

    @NotEmpty(message = "列不能为空")
    @Schema(description = "字段")
    private String field;

    @Schema(description = "列别名")
    private String title;

    @Schema(description = "宽度")
    private Integer width;

    @NotNull(message = "排序不能为空")
    @Schema(description = "排序")
    private Integer sort;

    @NotNull(message = "显示不能为空")
    @Schema(description = "是否显示")
    private Boolean showFlag;

    /**
     * 后台使用
     */
    @Schema(description = "是否可见，后台返回，前台不传")
    private Boolean hidden;

    @Schema(description = "角色id")
    private String roleId;

    @Schema(description = "用户id")
    private String employeeId;

    @Schema(description = "角色列表")
    private List<RoleVO> roleList;

    @Schema(description = "用户列表")
    private List<EmployeeVO> employeeList;

}
