package com.o2o.massage.builder;

import com.o2o.massage.dao.entity.OrderInfo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: zhongli
 * @Date: 2018/3/12 11:25
 * @Description:
 */
public class OrderInfoDataBuilder {
    private Long orderId;
    private String orderNo;
    private String customerUserId;
    private String customerName;
    private String providerUserId;
    private String providerName;
    private String memberId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Byte orderStatus;
    private Short cityId;
    private String address;
    private Byte commentStatus;
    private String contactPhone;
    private String contactMan;
    private String serviceId;
    private String serviceName;
    private BigDecimal journeyFee;
    private BigDecimal servicePrice;
    private Integer serviceCount;
    private BigDecimal totalAmount;
    private Date bookStartTime;
    private Date bookEndTime;
    private Date departureTime;
    private Date realServStartTime;
    private Date realServDoneTime;
    private Byte cancelReason;
    private Date cancelTime;
    private Date createTime;
    private Date customerConfirmTime;
    private Date providerConfirmTime;
    private Date providerGrabTime;
    private Date updateTime;
    private String updateBy;
    private String createBy;
    private Integer version;
    private Date stateExpireTime;

    public OrderInfoDataBuilder orderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public OrderInfoDataBuilder orderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public OrderInfoDataBuilder customerUserId(String customerUserId) {
        this.customerUserId = customerUserId;
        return this;
    }

    public OrderInfoDataBuilder customerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public OrderInfoDataBuilder providerUserId(String providerUserId) {
        this.providerUserId = providerUserId;
        return this;
    }

    public OrderInfoDataBuilder providerName(String providerName) {
        this.providerName = providerName;
        return this;
    }

    public OrderInfoDataBuilder memberId(String memberId) {
        this.memberId = memberId;
        return this;
    }

    public OrderInfoDataBuilder latitude(BigDecimal latitude) {
        this.latitude = latitude;
        return this;
    }

    public OrderInfoDataBuilder longitude(BigDecimal longitude) {
        this.longitude = longitude;
        return this;
    }

    public OrderInfoDataBuilder orderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public OrderInfoDataBuilder cityId(Short cityId) {
        this.cityId = cityId;
        return this;
    }

    public OrderInfoDataBuilder address(String address) {
        this.address = address;
        return this;
    }

    public OrderInfoDataBuilder commentStatus(Byte commentStatus) {
        this.commentStatus = commentStatus;
        return this;
    }

    public OrderInfoDataBuilder contactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
        return this;
    }

    public OrderInfoDataBuilder contactMan(String contactMan) {
        this.contactMan = contactMan;
        return this;
    }

    public OrderInfoDataBuilder serviceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public OrderInfoDataBuilder serviceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public OrderInfoDataBuilder journeyFee(BigDecimal journeyFee) {
        this.journeyFee = journeyFee;
        return this;
    }

    public OrderInfoDataBuilder servicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
        return this;
    }

    public OrderInfoDataBuilder bookStartTime(Date bookStartTime) {
        this.bookStartTime = bookStartTime;
        return this;
    }

    public OrderInfoDataBuilder bookEndTime(Date bookEndTime) {
        this.bookEndTime = bookEndTime;
        return this;
    }

    public OrderInfoDataBuilder departureTime(Date departureTime) {
        this.departureTime = departureTime;
        return this;
    }

    public OrderInfoDataBuilder realServStartTime(Date realServStartTime) {
        this.realServStartTime = realServStartTime;
        return this;
    }

    public OrderInfoDataBuilder realServDoneTime(Date realServDoneTime) {
        this.realServDoneTime = realServDoneTime;
        return this;
    }

    public OrderInfoDataBuilder cancelReason(Byte cancelReason) {
        this.cancelReason = cancelReason;
        return this;
    }

    public OrderInfoDataBuilder cancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
        return this;
    }

    public OrderInfoDataBuilder createTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public OrderInfoDataBuilder customerConfirmTime(Date customerConfirmTime) {
        this.customerConfirmTime = customerConfirmTime;
        return this;
    }

    public OrderInfoDataBuilder providerConfirmTime(Date providerConfirmTime) {
        this.providerConfirmTime = providerConfirmTime;
        return this;
    }

    public OrderInfoDataBuilder providerGrabTime(Date providerGrabTime) {
        this.providerGrabTime = providerGrabTime;
        return this;
    }

    public OrderInfoDataBuilder stateExpireTime(Date stateExpireTime) {
        this.stateExpireTime = stateExpireTime;
        return this;
    }

    public OrderInfoDataBuilder updateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public OrderInfoDataBuilder updateBy(String updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public OrderInfoDataBuilder createBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    public OrderInfoDataBuilder version(Integer version) {
        this.version = version;
        return this;
    }

    public OrderInfoDataBuilder serviceCount(Integer serviceCount) {
        this.serviceCount = serviceCount;
        return this;
    }

    public OrderInfoDataBuilder totalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public OrderInfo build() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(this.orderId);
        orderInfo.setOrderNo(this.orderNo);
        orderInfo.setCustomerUserId(this.customerUserId);
        orderInfo.setCustomerName(this.customerName);
        orderInfo.setProviderUserId(this.providerUserId);
        orderInfo.setProviderName(this.providerName);
        orderInfo.setMemberId(this.memberId);
        orderInfo.setLatitude(this.latitude);
        orderInfo.setLongitude(this.longitude);
        orderInfo.setOrderStatus(this.orderStatus);
        orderInfo.setCityId(this.cityId);
        orderInfo.setAddress(this.address);
        orderInfo.setCommentStatus(this.commentStatus);
        orderInfo.setContactPhone(this.contactPhone);
        orderInfo.setContactMan(this.contactMan);
        orderInfo.setServiceId(this.serviceId);
        orderInfo.setServiceName(this.serviceName);
        orderInfo.setJourneyFee(this.journeyFee);
        orderInfo.setServicePrice(this.servicePrice);
        orderInfo.setServiceCount(this.serviceCount);
        orderInfo.setTotalAmount(this.totalAmount);
        orderInfo.setBookStartTime(this.bookStartTime);
        orderInfo.setBookEndTime(this.bookEndTime);
        orderInfo.setDepartureTime(this.departureTime);
        orderInfo.setRealServStartTime(this.realServStartTime);
        orderInfo.setRealServDoneTime(this.realServDoneTime);
        orderInfo.setCancelReason(this.cancelReason);
        orderInfo.setCancelTime(this.cancelTime);
        orderInfo.setCreateTime(this.createTime);
        orderInfo.setCustomerConfirmTime(this.customerConfirmTime);
        orderInfo.setProviderConfirmTime(this.providerConfirmTime);
        orderInfo.setProviderGrabTime(this.providerGrabTime);
        orderInfo.setStateExpireTime(this.stateExpireTime);
        orderInfo.setUpdateTime(this.updateTime);
        orderInfo.setUpdateBy(this.updateBy);
        orderInfo.setVersion(this.version);
        orderInfo.setCreateBy(this.createBy);
        return orderInfo;
    }
}
