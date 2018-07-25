package cn.jeeweb.modules.sys.utils;

import cn.jeeweb.core.utils.CacheUtils;
import cn.jeeweb.core.utils.SpringContextHolder;
import cn.jeeweb.core.utils.StringUtils;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.o2o.nm.entity.UserInfo;
import com.o2o.nm.sys.entity.Permission;
import com.o2o.nm.sys.entity.SysRole;
import com.o2o.nm.sys.security.realm.UserRealm.Principal;
import com.o2o.nm.sys.service.MenuService;
import com.o2o.nm.sys.service.PermissionService;
import com.o2o.nm.sys.service.RoleService;
import com.o2o.nm.sys.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UserUtils {

    private static UserService userService = SpringContextHolder.getBean(UserService.class);
    private static RoleService roleService = SpringContextHolder.getBean(RoleService.class);
    private static MenuService menuService = SpringContextHolder.getBean(MenuService.class);
    private static PermissionService permissionService = SpringContextHolder.getBean(PermissionService.class);
    public static final String USER_CACHE = "userCache";
    public static final String USER_CACHE_ID_ = "id_";
    public static final String CACHE_ROLE_LIST = "roleList";
    public static final String CACHE_MENU_LIST = "menuList";
    public static final String CACHE_PERMISSION_LIST = "permissionList";


    public static Map<String, String> doAuthorizationPermission(String userId) {

        List<Permission> permissions = permissionService.getPermissionsByUserId(userId, null);
        Map<String, String> urlMapping = Maps.newHashMap();
        permissions.forEach(permission -> {
            String url = permission.getUrl();
            int index = url.indexOf("?");
            if (index > 0) {
                url = url.substring(0, index);
            }
            urlMapping.put(url, "");
        });
        return urlMapping;
    }

    /**
     * 根据ID获取用户
     *
     * @return 取不到返回null
     */
    public static UserInfo get(String id) {
        UserInfo user = CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
        if (user == null) {
            user = userService.selectById(id);
            if (user == null) {
                return null;
            }
            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
        }
        return user;
    }

    public static String getUserPermissionList(String userId) {
        return CACHE_PERMISSION_LIST + "_" + userId;
    }

    /**
     * 清除当前用户缓存
     */
    public static void clearCache() {
        removeCache(CACHE_ROLE_LIST);
        removeCache(CACHE_MENU_LIST);
        removeCache(CACHE_PERMISSION_LIST);
        clearCache(getUser());
    }

    public static void clearMenuCache() {
        removeCache(CACHE_MENU_LIST);
    }

    public static void clearRoleCache() {
        removeCache(CACHE_ROLE_LIST);
    }

    /**
     * 清除指定用户缓存
     */
    public static void clearCache(UserInfo user) {
        CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
    }

    /**
     * 获取当前用户
     *
     * @return 取不到返回 new User()
     */
    public static UserInfo getUser() {
        Principal principal = getPrincipal();
        if (principal != null) {
            UserInfo user = get(principal.getId());
            if (user != null) {
                return user;
            }
            return new UserInfo();
        }
        // 如果没有登录，则返回实例化空的User对象。
        return new UserInfo();
    }

    /**
     * 获取当前用户角色列表
     */
    public static List<SysRole> getRoleList() {
        List<SysRole> roleList = (List<SysRole>) getCache(CACHE_ROLE_LIST);
        if (roleList == null) {
            UserInfo user = getUser();
            roleList = roleService.getRolesByUser(user);
            putCache(CACHE_ROLE_LIST, roleList);
        }
        return roleList;
    }

    public static Set<String> getRoleStringList() {
        Set<SysRole> roles = Sets.newConcurrentHashSet(getRoleList());
        return roles.stream().map(role -> role.getId()).collect(Collectors.toSet());
    }

    /**
     * 获取当前用户授权菜单
     */
    public static List<Permission> getMenuList() {
        List<Permission> permissionList = (List<Permission>) getCache(CACHE_MENU_LIST);
        if (permissionList == null) {
            permissionList = menuService.findMenuByUser(getUser());
            putCache(CACHE_MENU_LIST, permissionList);
        }
        return permissionList;
    }


    public static Set<String> getPermissionsList() {
        List<Permission> list = UserUtils.getMenuList();
        Set<String> permissionsList = Sets.newConcurrentHashSet();
        for (Permission menu : list) {
            if (StringUtils.isNotBlank(menu.getPermission())) {
                // 添加基于Permission的权限信息
                for (String permission : StringUtils.split(menu.getPermission(), ",")) {
                    if (StringUtils.isNotBlank(permission)) {
                        permissionsList.add(permission);
                    }
                }
            }
        }
        return permissionsList;
    }

    /**
     * 获取授权主要对象
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前登录者对象
     */
    public static Principal getPrincipal() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal) subject.getPrincipal();
            if (principal != null) {
                return principal;
            }
            // subject.logout();
        } catch (UnavailableSecurityManagerException e) {

        } catch (InvalidSessionException e) {

        }
        return null;
    }

    public static Session getSession() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null) {
                session = subject.getSession();
            }
            if (session != null) {
                return session;
            }
            // subject.logout();
        } catch (InvalidSessionException e) {

        }
        return null;
    }

    // ============== User Cache ==============
    public static Object getCache(String key) {
        return getCache(key, null);
    }

    public static Object getCache(String key, Object defaultValue) {
        Object obj = getSession().getAttribute(key);
        return obj == null ? defaultValue : obj;
    }

    public static void putCache(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static void removeCache(String key) {
        getSession().removeAttribute(key);
    }

}
