package com.o2o.nm.sys.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.o2o.nm.entity.UserInfo;
import com.o2o.nm.sys.entity.SysRole;
import com.o2o.nm.sys.vo.SearchUser;

import java.util.List;

/**
 * <p>
 * 系统用户基本信息表 服务类
 * </p>
 *
 * @author warning5
 * @since 2018-02-22
 */
public interface UserService extends IService<UserInfo> {


    void unlockUsers(List<String> userIds) throws Exception;

    UserInfo findByUserName(String username);

    boolean saveUser(UserInfo userInfo) throws Exception;

    UserInfo getUserById(String userId);

    void deleteUsers(List<String> ids);

    Page<UserInfo> selectUserPage(Page<UserInfo> page, SearchUser searchUser, int offset);

    List<SysRole> getUserRoles(String userId);

    void assignRoles(String userId, String roles);

    void updateUserLoginInfo(String userId);

    void lockUsers(List<String> userIds) throws Exception;
}
