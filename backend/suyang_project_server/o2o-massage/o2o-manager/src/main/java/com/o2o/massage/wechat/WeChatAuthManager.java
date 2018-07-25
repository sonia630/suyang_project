package com.o2o.massage.wechat;

import com.o2o.massage.core.utils.HttpClientHelper;
import com.o2o.massage.core.utils.XStreamUtils;
import com.o2o.massage.wechat.entity.AuthAccessToken;
import com.o2o.massage.wechat.entity.AuthTokenParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: zhongli
 * @Date: 2018/3/21 16:39
 * @Description: http://blog.csdn.net/phil_jing/article/details/53914588
 */
@Component
public class WeChatAuthManager {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public AuthAccessToken getAuthAccessTokenByCode(String code) {
        AuthTokenParams params = new AuthTokenParams();
        params.setAppid(WeChatConfig.getAppId());
        params.setSecret(WeChatConfig.getAppSecret());
        params.setCode(code);
        return getAuthAccessToken(params);
    }

    /**
     * 获取网页授权凭证
     *
     * @param params
     * @return
     */
    public AuthAccessToken getAuthAccessToken(AuthTokenParams params) {
        AuthAccessToken authAccessToken = null;
        //获取网页授权凭证
        try {
            String result = HttpClientHelper.doGet(WeChatConstants.GET_AUTHTOKEN_URL, params.getParams(), null);
            if (StringUtils.isNotBlank(result)) {
                authAccessToken = XStreamUtils.parse(result, AuthAccessToken.class);
            }
        } catch (Exception e) {
            logger.error("getAuthAccessToken error:", e);
        }
        return authAccessToken;
    }

}
