package com.o2o.nm.sys.security.web.filter.authc;

import cn.jeeweb.core.utils.IpUtils;
import cn.jeeweb.core.utils.StringUtils;
import cn.jeeweb.modules.sys.utils.UserUtils;
import com.o2o.nm.sys.security.exception.RepeatAuthenticationException;
import com.o2o.nm.sys.security.realm.UserRealm.Principal;
import com.o2o.nm.sys.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(getClass());
    public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
    public static final String DEFAULT_MESSAGE_ERROR_PARAM = "error";

    private String captchaParam = DEFAULT_CAPTCHA_PARAM;
    private String messageErrorParam = DEFAULT_MESSAGE_ERROR_PARAM;

    @Autowired
    private UserService userService;

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        if (password == null) {
            password = "";
        }
        boolean rememberMe = isRememberMe(request);
        String host = IpUtils.getIpAddr((HttpServletRequest) request);
        String captcha = getCaptcha(request);
        return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha);
    }

    public String getCaptchaParam() {
        return captchaParam;
    }

    public void setCaptchaParam(String captchaParam) {
        this.captchaParam = captchaParam;
    }

    public void setMessageErrorParam(String messageErrorParam) {
        this.messageErrorParam = messageErrorParam;
    }

    public String getMessageErrorParam() {
        return messageErrorParam;
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        UserUtils.clearCache();
        WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
    }


    @Override
    protected boolean onLoginSuccess(AuthenticationToken token,
                                     Subject subject, ServletRequest request, ServletResponse response) throws Exception {

        Principal principal = (Principal) subject.getPrincipals().getPrimaryPrincipal();
        String userId = principal.getId();
        try {
            UserUtils.putCache(UserUtils.getUserPermissionList(userId), UserUtils.doAuthorizationPermission(userId));
            userService.updateUserLoginInfo(userId);
            if (logger.isInfoEnabled()) {
                logger.info("user " + userId + " login success.");
            }
        } catch (Exception e) {
            logger.error("{}", e);
            subject.logout();
        }
        return super.onLoginSuccess(token, subject, request, response);
    }


    /**
     * 登录失败调用事件
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
                                     ServletResponse response) {
        super.onLoginFailure(token, e, request, response);
        String className = e.getClass().getName(), message = "";
        if (IncorrectCredentialsException.class.getName().equals(className)
                || UnknownAccountException.class.getName().equals(className)) {
            message = "用户或密码错误, 请重试.";
        } else if (RepeatAuthenticationException.class.getName().equals(className)) {
            message = "请勿重复提交认证.";
        } else if (ExcessiveAttemptsException.class.getName().equals(className)) {
            message = "请勿重复提交认证,请半小时之后登录";
        } else if (StringUtils.isNoneBlank(e.getMessage())) {
            message = e.getMessage();
        } else {
            message = "系统出现点问题，请稍后再试！";
            logger.error("{}", e);
        }
        request.setAttribute(getFailureKeyAttribute(), className);
        request.setAttribute(getMessageErrorParam(), message);
        return true;
    }

}