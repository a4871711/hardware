package net.lab1024.sa.base.handler;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.base.common.exception.BusinessException;
import net.lab1024.sa.base.common.tenant.TableMetaInfoHelper;
import net.lab1024.sa.base.common.util.SmartRequestUtil;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Description 自定义租户处理器，用于 MyBatis Plus 插件中处理多租户逻辑
 * @Author 赵嘉伟
 * @Date 21:53 2025/3/6
 * @Version 1.0
 **/
@Slf4j
@Component
public class CustomTenantHandler implements TenantLineHandler {

    private static final String[] IGNORE_TABLE = new String[]{
            "t_company_invitation_code", // 公司登录邀请码，这个是所有公司共用的
            "t_subscription_plan_menu", // 订阅计划菜单，这个是所有公司共用的




      "t_config","t_dict_key","t_dict_value","t_serial_number","t_smart_job","t_smart_job_log",
            "t_login_log","t_login_fail","t_operate_log","information_schema","t_code_generator_config",
            "t_change_log",
            "t_feedback","t_table_column","t_heart_beat_record",
            "t_help_doc","t_help_doc_catalog","t_help_doc_relation","t_help_doc_view_record",
            "t_mail_template","t_role_employee","t_role_menu","t_password_log",
            "t_smart_job", "t_smart_job_log", "columns",
            "t_serial_number",
            "t_serial_number_record",
            "t_company_serial_number",

            "t_setting",


            /** TODO 下面待确定是否要弄多租户 */
            "information_schema.tables","tables",
            "t_message","t_notice","t_notice_type","t_notice_view_record","t_notice_visible_range",
            "t_reload_item","t_reload_result",

            "t_oa_bank",
            "t_oa_enterprise",
            "t_oa_enterprise_employee",
            "t_oa_invoice",

    };

    @Override
    public Expression getTenantId() {
        // 假设有一个租户上下文，能够从中获取当前用户的租户
        Long tenantId = SmartRequestUtil.getRequestCompanyId();
        if(Objects.isNull(tenantId)) {
            throw new BusinessException("？？？？？？？？？？？？？？？？？？？");
        }

        // 返回租户ID的表达式，LongValue 是 JSQLParser 中表示 bigint 类型的 class
        return new LongValue(tenantId);
    }

    @Override
    public String getTenantIdColumn() {
        return "company_id";
    }

    @Override
    public boolean ignoreTable(String tableName) {
        if (Arrays.asList(IGNORE_TABLE).contains(tableName)) {
            return true;
        }

        // 获取 noNeedLoginUrlList Bean
        List<String> noNeedLoginUrlList = SpringUtil.getBean("noNeedLoginUrlList");
        // 获取当前请求的 URL（假设通过某种方式获取，例如 ServletRequestAttributes）
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            String url = attributes.getRequest().getRequestURI();
            return noNeedLoginUrlList.contains(url);
        }

        if(!TableMetaInfoHelper.isTenantEntity(tableName)) {
            throw new BusinessException("tableName == " + tableName);
        }

        return !TableMetaInfoHelper.isTenantEntity(tableName);

    }
}