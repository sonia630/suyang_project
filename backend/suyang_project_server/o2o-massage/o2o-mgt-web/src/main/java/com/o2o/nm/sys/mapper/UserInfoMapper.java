package com.o2o.nm.sys.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.o2o.nm.entity.UserInfo;
import com.o2o.nm.sys.entity.SysRole;
import com.o2o.nm.sys.vo.SearchUser;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 系统用户基本信息表 Mapper 接口
 * </p>
 *
 * @author warning5
 * @since 2018-02-22
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    List<UserInfo> selectUserPage(Page<UserInfo> page, @Param("searchUser") SearchUser searchUser, @Param("offset") int offset, @Param("superAdmin")String superAdmin);

    boolean updateSysUserWithOutPwd(UserInfo userInfo);

    void cancelUser(List<String> ids, Integer status);

    List<SysRole> getUserRoles(String userId);

    void updateLoginInfo(@Param("date") Date date, @Param("userId") String userId);

    void changeStatus(@Param("userIds") List<String> userIds, @Param("status") Integer status);
}
