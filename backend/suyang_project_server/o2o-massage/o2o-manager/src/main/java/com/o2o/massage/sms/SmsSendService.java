package com.o2o.massage.sms;

/**
 * @Author: zhongli
 * @Date: 2018/2/23 11:48
 * @Description:
 */
public interface SmsSendService {

    int sendVerifyCode(String phoneNumber,String verifyCode);

    int sendNormalText(String phoneNumber,String text);
}
