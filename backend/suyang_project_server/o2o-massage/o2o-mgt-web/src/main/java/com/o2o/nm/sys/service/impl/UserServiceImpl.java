package com.o2o.nm.sys.service.impl;

import cn.jeeweb.core.query.wrapper.EntityWrapper;
import cn.jeeweb.core.utils.StringUtils;
import cn.jeeweb.modules.sys.Constants;
import cn.jeeweb.modules.sys.service.impl.PasswordService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.o2o.nm.entity.UserInfo;
import com.o2o.nm.sys.entity.SysRole;
import com.o2o.nm.sys.entity.SysUserRole;
import com.o2o.nm.sys.mapper.UserInfoMapper;
import com.o2o.nm.sys.service.SysUserRoleService;
import com.o2o.nm.sys.service.UserService;
import com.o2o.nm.sys.vo.SearchUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 系统用户基本信息表 服务实现类
 * </p>
 *
 * @author warning5
 * @since 2018-02-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    PasswordService passwordService;
    @Autowired
    SysUserRoleService sysUserRoleService;

    @Override
    public Page<UserInfo> selectUserPage(Page<UserInfo> page, SearchUser searchUser, int offset) {
        return page.setRecords(baseMapper.selectUserPage(page, searchUser, offset, Constants.SUPER_ADMIN));
    }

    @Override
    public List<SysRole> getUserRoles(String userId) {
        return baseMapper.getUserRoles(userId);
    }

    @Override
    public void assignRoles(String userId, String roles) {
        String[] rols = roles.split(",");
        sysUserRoleService.delete(new EntityWrapper<>(SysUserRole.class).eq("user_id", userId));
        if (StringUtils.isNotEmpty(roles)) {
            List<SysUserRole> param = Lists.newArrayListWithCapacity(rols.length);
            for (int i = 0; i < rols.length; i++) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(userId);
                sysUserRole.setRoleId(rols[i]);
                param.add(sysUserRole);
            }
            sysUserRoleService.insertBatch(param);
        }
    }

    @Override
    public void updateUserLoginInfo(String userId) {
        baseMapper.updateLoginInfo(new Date(), userId);
    }

    @Override
    public void lockUsers(List<String> userIds) throws Exception {
        if (userIds.contains(Constants.SUPER_ADMIN)) {
            throw new Exception("无法锁定超级管理员");
        }
        baseMapper.changeStatus(userIds, UserInfo.STATUS_LOCKED);
    }

    @Override
    public void unlockUsers(List<String> userIds) throws Exception {
        baseMapper.changeStatus(userIds, UserInfo.STATUS_NORMAL);
    }

    @Override
    public UserInfo findByUserName(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        return selectOne(new EntityWrapper<>(UserInfo.class).eq("name", username));
    }

    @Override
    public boolean saveUser(UserInfo userInfo) throws Exception {

        if (userInfo.getCheckedTypes() != null) {
            Integer value = 0;
            try {
                for (String v : userInfo.getCheckedTypes()) {
                    value += Integer.parseInt(v);
                }
            } catch (Exception e) {
                logger.error("{}", e);
            }
            userInfo.setUserType(value);

        }

        if (StringUtils.isNoneBlank(userInfo.getId())) {
            if (userInfo.getPassword() != null) {
                passwordService.encryptPassword(userInfo);
            }
            if (userInfo.getPassword() != null) {
                return updateById(userInfo);
            } else {
                return baseMapper.updateSysUserWithOutPwd(userInfo);
            }
        } else {
            UserInfo user = findByUserName(userInfo.getName());
            if (user != null) {
                throw new Exception("用户名称已存在");
            }
            passwordService.encryptPassword(userInfo);
            userInfo.setPhone(null);
            return insert(userInfo);
        }
    }

    @Override
    public void deleteUsers(List<String> ids) {
        baseMapper.cancelUser(ids, UserInfo.STATUS_DELETE);
    }

    @Override
    public UserInfo getUserById(String userId) {
        return selectById(userId);
    }
}
