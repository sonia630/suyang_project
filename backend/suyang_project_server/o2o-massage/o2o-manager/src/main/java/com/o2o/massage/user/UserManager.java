package com.o2o.massage.user;

import com.o2o.massage.dao.component.CustomerDao;
import com.o2o.massage.dao.component.ProviderDao;
import com.o2o.massage.dao.component.UserBaseDao;
import com.o2o.massage.dao.entity.CustomerInfo;
import com.o2o.massage.dao.entity.ProviderInfo;
import com.o2o.massage.dao.entity.UserBaseInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: zhongli
 * @Date: 2018/3/8 0:00
 * @Description:
 */
@Component
public class UserManager {

    @Resource
    private UserBaseDao userBaseDao;

    @Resource
    private ProviderDao providerDao;

    @Resource
    private CustomerDao customerDao;

    public ProviderEntityWrapper qryProviderByUserId(String providerUserId) {
        UserBaseInfo baseInfo = userBaseDao.getBaseUser(providerUserId);
        ProviderInfo providerInfo = providerDao.findOneByUserId(providerUserId);
        if (providerInfo == null || baseInfo == null) {
            return null;
        }
        return new ProviderEntityWrapper(baseInfo, providerInfo);
    }

    public CustomerEntityWrapper qryCustomerByUserId(String customerUserId) {
        UserBaseInfo baseInfo = userBaseDao.getBaseUser(customerUserId);
        CustomerInfo customerInfo = customerDao.findOneByUserId(customerUserId);
        if (customerInfo == null || baseInfo == null) {
            return null;
        }
        return new CustomerEntityWrapper(baseInfo, customerInfo);
    }
}
