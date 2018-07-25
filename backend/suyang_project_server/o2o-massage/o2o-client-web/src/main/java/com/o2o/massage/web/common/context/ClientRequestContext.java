package com.o2o.massage.web.common.context;

import com.o2o.massage.core.common.ApiRequestContext;
import com.o2o.massage.core.common.DeviceInfo;

/**
 * @Author: zhongli
 * @Date: 2018/2/6 22:25
 * @Description:
 */
public class ClientRequestContext extends ApiRequestContext {

    protected DeviceInfo deviceInfo;

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public static ClientRequestContext getCurrent() {
        return (ClientRequestContext)ApiRequestContext.getCurrent();
    }
}






