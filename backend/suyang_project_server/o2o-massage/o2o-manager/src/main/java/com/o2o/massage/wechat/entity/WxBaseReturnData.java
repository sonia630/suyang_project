package com.o2o.massage.wechat.entity;

import com.o2o.massage.wechat.WeChatConstants;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * @Author: zhongli
 * @Date: 2018/3/21 20:31
 * @Description:
 */
@Data
public class WxBaseReturnData {
    @XStreamAlias("return_code")
    protected String returnCode;

    @XStreamAlias("return_msg")
    protected String returnMsg;

    public static WxBaseReturnData returnSuccess() {
        WxBaseReturnData returnData = new WxBaseReturnData();
        returnData.setReturnCode(WeChatConstants.RETURN_SUCCESS);
        returnData.setReturnMsg("OK");
        return returnData;
    }

    public static WxBaseReturnData returnFail(String msg) {
        WxBaseReturnData returnData = new WxBaseReturnData();
        returnData.setReturnCode(WeChatConstants.RETURN_SUCCESS);
        returnData.setReturnMsg(msg);
        return returnData;
    }
}
