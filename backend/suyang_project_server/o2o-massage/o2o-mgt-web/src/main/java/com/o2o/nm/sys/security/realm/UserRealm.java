package com.o2o.nm.sys.security.realm;

import cn.jeeweb.core.utils.ServletUtils;
import cn.jeeweb.modules.sys.utils.LogUtils;
import cn.jeeweb.modules.sys.utils.UserUtils;
import com.o2o.nm.entity.UserInfo;
import com.o2o.nm.sys.security.web.filter.authc.UsernamePasswordToken;
import com.o2o.nm.sys.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权的回调方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(UserUtils.getRoleStringList());
        authorizationInfo.setStringPermissions(UserUtils.getPermissionsList());
        return authorizationInfo;
    }

    /**
     * 认证的回调方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken authcToken = (UsernamePasswordToken) token;
        String username = authcToken.getUsername();
        UserInfo user = userService.findByUserName(username);
        if (user == null) {
            // 没找到帐号
            throw new UnknownAccountException();
        }
        // 帐号锁定
        if (UserInfo.STATUS_LOCKED.equals(user.getStatus())) {
            throw new LockedAccountException();
        }
        // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                new Principal(user),
                user.getPassword(),
                ByteSource.Util.bytes(user.getName()),
                getName()
        );
        // 记录登录日志
        LogUtils.saveLog(ServletUtils.getRequest(), "系统登录");
        return authenticationInfo;
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

    /**
     * 授权用户信息
     */
    public static class Principal implements Serializable {

        private static final long serialVersionUID = 1L;

        private String id; // 编号
        private String username; // 登录名

        public Principal(UserInfo user) {
            this.id = user.getId();
            this.username = user.getName();
        }

        public String getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getSessionId() {
            try {
                return (String) UserUtils.getSession().getId();
            } catch (Exception e) {
                return "";
            }
        }

        @Override
        public String toString() {
            return id;
        }

    }
}
