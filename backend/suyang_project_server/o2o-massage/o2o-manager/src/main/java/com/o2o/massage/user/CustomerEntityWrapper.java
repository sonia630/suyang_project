package com.o2o.massage.user;

import com.o2o.massage.dao.entity.CustomerInfo;
import com.o2o.massage.dao.entity.UserBaseInfo;

/**
 * @Author: zhongli
 * @Date: 2018/3/8 0:08
 * @Description:
 */
public class CustomerEntityWrapper extends UserBaseInfo {
    private UserBaseInfo baseInfo;
    private CustomerInfo customerInfo;


    public CustomerEntityWrapper() {

    }

    public CustomerEntityWrapper(UserBaseInfo baseInfo, CustomerInfo customerInfo) {
        this.baseInfo = baseInfo;
        this.customerInfo = customerInfo;
    }

    public UserBaseInfo getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(UserBaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }
}
