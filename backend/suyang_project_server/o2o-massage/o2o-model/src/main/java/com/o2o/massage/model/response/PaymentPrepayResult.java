package com.o2o.massage.model.response;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhongli
 * @Date: 2018/3/21 21:08
 * @Description:
 */
@Data
public class PaymentPrepayResult {
    private String prepayId;
    private String appId;
    private String timeStamp;
    private String nonceStr;
    private String packageName;
    private String signType;
    private String paySign;

    public Map<String, String> toParaMap() {
        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("prepay_id", prepayId);
        paraMap.put("appid", appId);
        paraMap.put("timestamp", timeStamp);
        paraMap.put("nonce_str", nonceStr);
        paraMap.put("package", packageName);
        paraMap.put("sign_type", signType);
        return paraMap;
    }
}
