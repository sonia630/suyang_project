package com.o2o.massage.builder;

import com.o2o.massage.dao.entity.ServiceInfo;
import com.o2o.massage.model.ProviderSimpleInfoVO;
import com.o2o.massage.model.ServiceInfoVO;
import com.o2o.massage.user.ProviderEntityWrapper;
import org.springframework.beans.BeanUtils;

/**
 * @Author: zhongli
 * @Date: 2018/3/8 0:46
 * @Description:
 */
public class OrderBuilder {
    //public static OrderFormResult buildOrderFormResult()

    public static ProviderSimpleInfoVO buildProviderSimpleInfoVO(ProviderEntityWrapper providerEntityWrapper){
        ProviderSimpleInfoVO simpleInfoVO=new ProviderSimpleInfoVO();
        BeanUtils.copyProperties(providerEntityWrapper.getProviderInfo(),simpleInfoVO);
        BeanUtils.copyProperties(providerEntityWrapper.getBaseInfo(),simpleInfoVO);
        return simpleInfoVO;
    }

    public static ServiceInfoVO buildServiceInfoVO(ServiceInfo serviceInfo){
        ServiceInfoVO serviceInfoVO=new ServiceInfoVO();
        BeanUtils.copyProperties(serviceInfo,serviceInfoVO);
        return serviceInfoVO;
    }

}
