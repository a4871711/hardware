package net.lab1024.sa.base.common.util;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.base.common.constant.StringConst;
import net.lab1024.sa.base.common.domain.RequestUser;

/**
 * 请求用户  工具类
 *
 * @Author 1024创新实验室: 罗伊
 * @Date 2022-05-30 21:22:12
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Slf4j
public class SmartRequestUtil {

    private static final ThreadLocal<RequestUser> REQUEST_THREAD_LOCAL = new ThreadLocal<>();

    public static void setRequestUser(RequestUser requestUser) {
        REQUEST_THREAD_LOCAL.set(requestUser);
    }

    public static RequestUser getRequestUser() {
        return REQUEST_THREAD_LOCAL.get();
    }

    public static Long getRequestUserId() {
        RequestUser requestUser = getRequestUser();
        return null == requestUser ? null : requestUser.getUserId();
    }

    public static String getRequestUserName() {
        RequestUser requestUser = getRequestUser();
        return null == requestUser ? null : requestUser.getUserName();
    }

    public static Long getRequestCompanyId() {
        RequestUser requestUser = getRequestUser();
        return null == requestUser ? getCompanyIdByLoginId((String) StpUtil.getLoginIdByToken(StpUtil.getTokenValue())) : requestUser.getCompanyId();
    }


    public static void remove() {
        REQUEST_THREAD_LOCAL.remove();
    }

    static Long getCompanyIdByLoginId(String loginId) {

        if (loginId == null) {
            return null;
        }

        try {
            String[] split = loginId.split(StringConst.COLON);
            // 如果是 万能密码 登录的用户
            String employeeIdStr;
            if (loginId.startsWith("S")) {
                employeeIdStr = split[3];
            } else {
                employeeIdStr = split[2];
            }

            return Long.parseLong(employeeIdStr);
        } catch (Exception e) {
            log.error("loginId parse error , loginId : {}", loginId, e);
            return null;
        }
    }


}
