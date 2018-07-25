
package com.o2o.nm.sys.security.web.filter.user;

import cn.jeeweb.core.utils.ServletUtils;
import cn.jeeweb.modules.sys.utils.UserUtils;
import com.google.common.collect.Maps;
import com.o2o.nm.entity.UserInfo;
import com.o2o.nm.entity.UserType;
import lombok.Setter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public class SysUserFilter extends UserFilter {

    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 用户删除了后重定向的地址
     */
    private String userNotfoundUrl;
    @Setter
    Map<String, String> no_auth = Maps.newHashMap();
    /**
     * 用户锁定后重定向的地址
     */
    private String userLockedUrl;
    /**
     * 未知错误
     */
    private String userUnknownErrorUrl;

    public String getUserNotfoundUrl() {
        return userNotfoundUrl;
    }

    public void setUserNotfoundUrl(String userNotfoundUrl) {
        this.userNotfoundUrl = userNotfoundUrl;
    }

    public String getUserLockedUrl() {
        return userLockedUrl;
    }

    public void setUserLockedUrl(String userLockedUrl) {
        this.userLockedUrl = userLockedUrl;
    }

    public String getUserUnknownErrorUrl() {
        return userUnknownErrorUrl;
    }

    public void setUserUnknownErrorUrl(String userUnknownErrorUrl) {
        this.userUnknownErrorUrl = userUnknownErrorUrl;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        if (isLoginRequest(request, response)) {
            return true;
        } else {
            UserInfo userInfo = UserUtils.getUser();

            if (!getSubject(request, response).isAuthenticated()) {
                return false;
            }

            if (userInfo != null && userInfo.isAdmin()) {
                return true;
            } else if (UserInfo.STATUS_DELETE.equals(userInfo.getStatus()) || UserInfo.STATUS_LOCKED.equals(userInfo.getStatus()) ||
                    (userInfo.getUserType() & UserType.SYS.getCode()) != 1) {
                getSubject(request, response).logout();
                try {
                    redirectToLogin(request, response);
                } catch (IOException e) {
                    logger.error("{}", e);
                }
                return false;
            } else {
                String url = ServletUtils.getUrl((HttpServletRequest) request);

                if (no_auth.containsKey(url)) {
                    return true;
                }

                Map<String, String> mapping = (Map<String, String>) UserUtils.getCache(UserUtils.getUserPermissionList(UserUtils.getUser().getId()));

                if (mapping == null) {
                    super.isAccessAllowed(request, response, mappedValue);
                }
                if (!mapping.containsKey(url)) {
                    logger.error("user " + UserUtils.getUser() + " can't auth " + url);
                    return false;
                } else {
                    return true;
                }
            }

        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        getSubject(request, response).logout();
        saveRequestAndRedirectToLogin(request, response);
        return true;
    }

    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        UserInfo user = UserUtils.getUser();
        String url;
        if (UserInfo.STATUS_DELETE.equals(user.getStatus())) {
            url = getUserNotfoundUrl();
        } else if (UserInfo.STATUS_LOCKED.equals(user.getStatus())) {
            url = getUserLockedUrl();
        } else {
            url = getUserUnknownErrorUrl();
        }
        WebUtils.issueRedirect(request, response, url);
    }

}
