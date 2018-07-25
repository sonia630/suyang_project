package com.o2o.massage.biz.impl;

import com.o2o.massage.biz.ClientTokenBiz;
import com.o2o.massage.dao.component.LoginTokenDao;
import com.o2o.massage.dao.entity.LoginToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: zhongli
 * @Date: 2018/2/6 17:47
 * @Description:
 */
@Service
public class ClientTokenBizImpl implements ClientTokenBiz{

    @Autowired
    private LoginTokenDao loginTokenDao;

    @Override
    public LoginToken getAppTokenInfoByToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return loginTokenDao.getLoginTokenByToken(token);
    }

    public void updateExpireTime(Long id, Date expireDate) {
        loginTokenDao.updateExpireTime(id,expireDate);
    }
}
