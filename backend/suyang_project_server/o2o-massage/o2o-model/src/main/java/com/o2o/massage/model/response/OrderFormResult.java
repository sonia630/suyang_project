package com.o2o.massage.model.response;

import com.o2o.massage.model.ProviderDayHourInfo;
import com.o2o.massage.model.ProviderSimpleInfoVO;
import com.o2o.massage.model.ServiceInfoVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: zhongli
 * @Date: 2018/3/4 13:08
 * @Description:
 */
@Data
public class OrderFormResult {
    private ProviderSimpleInfoVO provider;
    private ServiceInfoVO serviceInfo;
    private List<ProviderDayHourInfo> dayFreeHoursList;
    /*private BigDecimal servicePrice;*/
    private BigDecimal journeyFee;
    private BigDecimal totalFee;
}
