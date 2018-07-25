
package com.o2o.massage.dao.component;


import com.o2o.massage.dao.entity.LoginToken;
import com.o2o.massage.dao.entity.LoginTokenExample;
import com.o2o.massage.dao.mapper.LoginTokenMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by lawrence on 08/08/2017.
 */

@Component
public class LoginTokenDao {

    @Autowired
    private LoginTokenMapper tokenInfoMapper;

    public int addLoginTokenInfo(String userId, String deviceId, String token, Date createTime, Date expireTime) {
        LoginToken LoginToken = new LoginToken();
        LoginToken.setUserId(userId);
        LoginToken.setDeviceId(deviceId);
        LoginToken.setLoginToken(token);
        LoginToken.setCreateTime(createTime);
        LoginToken.setExpireTime(expireTime);
        return tokenInfoMapper.insertSelective(LoginToken);
    }

    public LoginToken getLoginToken(String userId, String imei) {
        LoginTokenExample example = new LoginTokenExample();
        example.createCriteria().andUserIdEqualTo(userId).andDeviceIdEqualTo(imei);
        example.setOrderByClause("expire_time desc");

        List<LoginToken> tokenInfoList = tokenInfoMapper.selectByExample(example);
        if (tokenInfoList != null && tokenInfoList.size() > 0) {
            return tokenInfoList.get(0);
        }
        return null;
    }

    public LoginToken getLoginTokenByToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        LoginTokenExample example = new LoginTokenExample();
        example.createCriteria().andLoginTokenEqualTo(token);

        List<LoginToken> tokenInfoList = tokenInfoMapper.selectByExample(example);
        if (tokenInfoList != null && tokenInfoList.size() > 0) {
            return tokenInfoList.get(0);
        }
        return null;
    }

    public void updateExpireTime(Long id, Date expireDate) {
        LoginToken LoginToken = new LoginToken();
        LoginToken.setExpireTime(expireDate);
        tokenInfoMapper.updateByPrimaryKeySelective(LoginToken);
    }

    public int deleteAppTokenInfo(String userId, String imei) {
        LoginTokenExample example = new LoginTokenExample();
        example.createCriteria().andUserIdEqualTo(userId).andDeviceIdEqualTo(imei);
        return tokenInfoMapper.deleteByExample(example);
    }
}
