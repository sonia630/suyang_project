package com.o2o.massage.web.client.controllers;

import com.o2o.massage.core.exception.BizException;
import com.o2o.massage.core.utils.XStreamUtils;
import com.o2o.massage.web.common.context.ApiRequestMethod;
import com.o2o.massage.wechat.entity.WxBaseReturnData;
import com.o2o.massage.wechat.entity.WxPayResultNotifyEntity;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @Author: zhongli
 * @Date: 2018/3/21 16:08
 * @Description:
 */
@Controller
@RequestMapping("/payment")
public class PaymentNotifyController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/wechat/resultNotify", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "微信支付结果通知", httpMethod = "POST")
    @ApiRequestMethod
    public String wechatResultNofity(HttpServletRequest request) throws BizException {
        WxBaseReturnData returnData = null;
        try {
            InputStream is = request.getInputStream();
            String xml = inputStream2String(is, "UTF-8");
            WxPayResultNotifyEntity resultNotifyEntity = XStreamUtils.parse(xml, WxPayResultNotifyEntity.class);

            returnData = WxBaseReturnData.returnSuccess();
        } catch (Exception ex) {
            returnData = WxBaseReturnData.returnFail(ex.getMessage());
        }
        return XStreamUtils.toXml(returnData);
    }

    @RequestMapping(value = "/wechat/refundNotify", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "微信退款结果通知", httpMethod = "POST")
    @ApiRequestMethod
    public String wechatRefundNofity(HttpServletRequest request) throws BizException {
        WxBaseReturnData returnData = null;
        try {
            returnData = WxBaseReturnData.returnSuccess();
        } catch (Exception ex) {
            returnData = WxBaseReturnData.returnFail(ex.getMessage());
        }
        return XStreamUtils.toXml(returnData);
    }


    /**
     * InputStream流转换成String字符串
     *
     * @param inStream InputStream流
     * @param encoding 编码格式
     * @return String字符串
     */
    public static String inputStream2String(InputStream inStream, String encoding) {
        int _buffer_size = 2048;
        String result = null;
        try {
            if (inStream != null) {
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] tempBytes = new byte[_buffer_size];
                int count = -1;
                while ((count = inStream.read(tempBytes, 0, _buffer_size)) != -1) {
                    outStream.write(tempBytes, 0, count);
                }
                tempBytes = null;
                outStream.flush();
                result = new String(outStream.toByteArray(), encoding);
            }
        } catch (Exception e) {
            result = null;
        }
        return result;
    }
}
