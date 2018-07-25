package com.o2o.nm;

import com.o2o.massage.biz.ClientLoginBiz;
import com.o2o.massage.biz.ClientUserBiz;
import com.o2o.massage.core.common.DeviceInfo;
import com.o2o.massage.dao.entity.type.EnumUserType;
import com.o2o.massage.model.request.LoginByPhoneRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @Author: zhongli
 * @Date: 2018/4/6 13:31
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-config.xml"})
public class UserTest {

    @Resource
    private ClientUserBiz clientUserBiz;

    @Resource
    private ClientLoginBiz clientLoginBiz;

    @Test
    public void addCustomerLogin() {
        String telephone = "15201291846";
        LoginByPhoneRequest request = new LoginByPhoneRequest();
        request.setPhone(telephone);
        request.setVerifyCode("4567");
        clientLoginBiz.login(request, EnumUserType.CUSTOMER, new DeviceInfo());
    }

    @Test
    public void addProviderLogin() {
        String telephone = "15201291847";
        LoginByPhoneRequest request = new LoginByPhoneRequest();
        request.setPhone(telephone);
        request.setVerifyCode("4567");
        clientLoginBiz.login(request, EnumUserType.PROVIDER, new DeviceInfo());
    }
}
