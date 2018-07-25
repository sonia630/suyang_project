package com.o2o.massage.dao.component;

import com.o2o.massage.dao.entity.UserBaseInfo;
import com.o2o.massage.dao.entity.UserBaseInfoExample;
import com.o2o.massage.dao.mapper.UserBaseInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Author: zhongli
 * @Date: 2018/2/7 16:35
 * @Description:
 */
@Component
public class UserBaseDao {

    @Autowired
    private UserBaseInfoMapper userBaseInfoMapper;

    /**
     * 获取用户基本信息
     */
    public UserBaseInfo getBaseUser(String userId) {
        return userBaseInfoMapper.selectByPrimaryKey(userId);
    }

    /**
     * 根据手机号查询用户
     */
    public UserBaseInfo getUserInfoByPhone(String phone) {
        UserBaseInfoExample userExample = new UserBaseInfoExample();
        UserBaseInfoExample.Criteria criteria = userExample.createCriteria();
        criteria.andPhoneEqualTo(phone);

        List<UserBaseInfo> users = userBaseInfoMapper.selectByExample(userExample);
        if (users != null && users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    public void addOneUser(UserBaseInfo record) {
        userBaseInfoMapper.insertSelective(record);
    }

    public void updateOneUser(UserBaseInfo record) {
        userBaseInfoMapper.updateByPrimaryKeySelective(record);
    }

    public void updatePassword(String userId, String password) {
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setUserId(userId);
        userBaseInfo.setPassword(password);
        before(userBaseInfo,userId);
        userBaseInfoMapper.updateByPrimaryKeySelective(userBaseInfo);
    }


    public void updateHeadUrl(String userId, String headUrl) {
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setUserId(userId);
        userBaseInfo.setHeadPic(headUrl);
        before(userBaseInfo,userId);
        userBaseInfoMapper.updateByPrimaryKeySelective(userBaseInfo);
    }

    public void updateUserName(String userId, String userName) {
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setUserId(userId);
        userBaseInfo.setName(userName);
        before(userBaseInfo,userId);
        userBaseInfoMapper.updateByPrimaryKeySelective(userBaseInfo);
    }

    public void updateRealName(String userId, String userName) {
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setUserId(userId);
        userBaseInfo.setRealName(userName);
        before(userBaseInfo,userId);
        userBaseInfoMapper.updateByPrimaryKeySelective(userBaseInfo);
    }

    public void updateTel(String userId, String tel) {
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setUserId(userId);
        userBaseInfo.setPhone(tel);
        before(userBaseInfo,userId);
        userBaseInfoMapper.updateByPrimaryKeySelective(userBaseInfo);
    }

    public void updateSex(String userId, byte sex) {
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setUserId(userId);
        userBaseInfo.setGender(sex);
        before(userBaseInfo,userId);
        userBaseInfoMapper.updateByPrimaryKeySelective(userBaseInfo);
    }

    public void updateBirthday(String userId, Date date) {
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setUserId(userId);
        userBaseInfo.setBirthday(date);
        before(userBaseInfo,userId);
        userBaseInfoMapper.updateByPrimaryKeySelective(userBaseInfo);
    }

    private void before(UserBaseInfo userBaseInfo, String userId) {
        userBaseInfo.setUpdateTime(new Date());
        userBaseInfo.setUpdateBy(userId);
    }
}
