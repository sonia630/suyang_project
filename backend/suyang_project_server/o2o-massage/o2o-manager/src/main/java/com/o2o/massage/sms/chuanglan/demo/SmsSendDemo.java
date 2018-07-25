package com.o2o.massage.sms.chuanglan.demo;

import com.alibaba.fastjson.JSON;
import com.o2o.massage.sms.chuanglan.request.SmsSendRequest;
import com.o2o.massage.sms.chuanglan.response.SmsSendResponse;
import com.o2o.massage.sms.chuanglan.util.ChuangLanSmsUtil;

import java.io.UnsupportedEncodingException;

/**
 * 
 * @author tianyh 
 * @Description:普通短信发送
 */
public class SmsSendDemo {

	public static final String charset = "utf-8";
	// 用户平台API账号(非登录账号,示例:N1234567)
	public static String account = "N3500212";
	// 用户平台API密码(非登录密码)
	public static String pswd = "LTgJpFSNev602d";

	public static void main(String[] args) throws UnsupportedEncodingException {

		//请求地址请登录253云通讯自助通平台查看或者询问您的商务负责人获取
		String smsSingleRequestServerUrl = "http://smssh1.253.com/msg/send/json";
		// 短信内容
	    String msg = "【253云通讯】你好,你的验证码是789012";
		//手机号码
		String phone = "15201291846";
		//状态报告
		String report= "true";
		
		SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, msg, phone,report);
		
		String requestJson = JSON.toJSONString(smsSingleRequest);
		
		System.out.println("before request string is: " + requestJson);
		long startTime=System.currentTimeMillis();
		String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);
		
		System.out.println("response after request result is :" + response);
		System.out.println("time cost is :" + (System.currentTimeMillis()-startTime));
		SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
		
		System.out.println("response  toString is :" + smsSingleResponse);
		
	
	}


}
