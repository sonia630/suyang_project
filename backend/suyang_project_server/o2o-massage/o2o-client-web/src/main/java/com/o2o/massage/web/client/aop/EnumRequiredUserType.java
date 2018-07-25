package com.o2o.massage.web.client.aop;


import com.o2o.massage.dao.entity.type.EnumUserType;

/**
 * @Author: zhongli
 * @Date: 2018/2/6 12:21
 * @Description:
 */
public enum EnumRequiredUserType {
    COMMON_USER(EnumUserType.CUSTOMER),
    SERVICE_PROVIDER(EnumUserType.PROVIDER),
    EITHER(EnumUserType.ALL);

    private EnumUserType userType;

    EnumRequiredUserType(EnumUserType userType) {
        this.userType = userType;
    }

    public EnumUserType getEnumUserType() {
        return userType;
    }

}
