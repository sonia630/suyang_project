package com.o2o.massage.srv;

import com.o2o.massage.dao.entity.ServiceInfo;

import java.math.BigDecimal;

/**
 * @Author: zhongli
 * @Date: 2018/3/9 19:49
 * @Description:
 */
public class QServiceInfoWithPrice extends ServiceInfo {
    private BigDecimal price;
    private Integer estimateTime;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(Integer estimateTime) {
        this.estimateTime = estimateTime;
    }
}
