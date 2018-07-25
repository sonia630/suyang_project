package com.o2o.massage.biz;

import com.o2o.massage.dao.entity.LoginToken;

/**
 * @Author: zhongli
 * @Date: 2018/2/6 17:46
 * @Description:
 */
public interface ClientTokenBiz {

    /**
     * 根据用户所传Token获取服务端保存的认证信息
     * @param token
     * @return
     */
    LoginToken getAppTokenInfoByToken(String token);

}
