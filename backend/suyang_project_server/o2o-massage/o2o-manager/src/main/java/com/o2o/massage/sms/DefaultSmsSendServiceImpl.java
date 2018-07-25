package com.o2o.massage.sms;

import com.alibaba.fastjson.JSON;
import com.o2o.massage.sms.chuanglan.request.SmsSendRequest;
import com.o2o.massage.sms.chuanglan.response.SmsSendResponse;
import com.o2o.massage.sms.chuanglan.util.ChuangLanSmsUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: zhongli
 * @Date: 2018/2/23 11:52
 * @Description:
 */
@Component
public class DefaultSmsSendServiceImpl implements SmsSendService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static final String charset = "utf-8";
    // 用户平台API账号(非登录账号,示例:N1234567)
    public static String account = "N3500212";
    // 用户平台API密码(非登录密码)
    public static String pswd = "LTgJpFSNev602d";

    String smsSingleRequestServerUrl = "http://smssh1.253.com/msg/send/json";

    private String prefix = "【253云通讯】";

    @Override
    public int sendVerifyCode(String phoneNumber, String verifyCode) {
        // 短信内容
        String msg = prefix + "您好，此次操作的验证码是" + verifyCode + ",有效时间为30分钟,如非本人操作请忽略。";

        String report = "true";
        SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, msg, phoneNumber, report);

        String requestJson = JSON.toJSONString(smsSingleRequest);

        logger.info("before request string is: " + requestJson);
        long startTime = System.currentTimeMillis();
        String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);

        logger.info("response after request result is :" + response);
        logger.info("time cost is :" + (System.currentTimeMillis() - startTime));
        if (StringUtils.isNotBlank(response)) {
            SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);

            logger.info("response  toString is :" + smsSingleResponse);
        }

        return 0;
    }

    @Override
    public int sendNormalText(String phoneNumber, String text) {
        // 短信内容
        String msg = prefix + text;

        String report = "true";
        SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, msg, phoneNumber, report);

        String requestJson = JSON.toJSONString(smsSingleRequest);

        logger.info("before request string is: " + requestJson);
        long startTime = System.currentTimeMillis();
        String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);

        logger.info("response after request result is :" + response);
        logger.info("time cost is :" + (System.currentTimeMillis() - startTime));
        if (StringUtils.isNotBlank(response)) {
            SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);

            logger.info("response  toString is :" + smsSingleResponse);
        }

        return 0;
    }
}
