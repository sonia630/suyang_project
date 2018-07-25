package com.o2o.massage.dao.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table order_info
 *
 * @mbg.generated do_not_delete_during_merge
 */
public class OrderInfo {
    /**
     * Database Column Remarks:
     *   订单自增id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.order_id
     *
     * @mbg.generated
     */
    private Long orderId;

    /**
     * Database Column Remarks:
     *   订单号，唯一
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.order_no
     *
     * @mbg.generated
     */
    private String orderNo;

    /**
     * Database Column Remarks:
     *   下单用户ID,关联user_info.user_id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.customer_user_id
     *
     * @mbg.generated
     */
    private String customerUserId;

    /**
     * Database Column Remarks:
     *   用户名称
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.customer_name
     *
     * @mbg.generated
     */
    private String customerName;

    /**
     * Database Column Remarks:
     *   服务提供者用户ID,关联user_info.user_id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.provider_user_id
     *
     * @mbg.generated
     */
    private String providerUserId;

    /**
     * Database Column Remarks:
     *   服务提供者名称
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.provider_name
     *
     * @mbg.generated
     */
    private String providerName;

    /**
     * Database Column Remarks:
     *   实际服务接收者ID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.member_id
     *
     * @mbg.generated
     */
    private String memberId;

    /**
     * Database Column Remarks:
     *   纬度
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.latitude
     *
     * @mbg.generated
     */
    private BigDecimal latitude;

    /**
     * Database Column Remarks:
     *   经度
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.longitude
     *
     * @mbg.generated
     */
    private BigDecimal longitude;

    /**
     * Database Column Remarks:
     *   订单状态。1-待顾问确认,2-待支付,3-待服务,4-服务中，5-交易完成,6-交易取消,,7-待抢单,8-待用户确认
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.order_status
     *
     * @mbg.generated
     */
    private Byte orderStatus;

    /**
     * Database Column Remarks:
     *   收货人的城市
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.city_id
     *
     * @mbg.generated
     */
    private Short cityId;

    /**
     * Database Column Remarks:
     *   收货人的详细地址
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.address
     *
     * @mbg.generated
     */
    private String address;

    /**
     * Database Column Remarks:
     *   点评状态；0，None；1，待评价；2，已评价
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.comment_status
     *
     * @mbg.generated
     */
    private Byte commentStatus;

    /**
     * Database Column Remarks:
     *   联系电话
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.contact_phone
     *
     * @mbg.generated
     */
    private String contactPhone;

    /**
     * Database Column Remarks:
     *   联系人
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.contact_man
     *
     * @mbg.generated
     */
    private String contactMan;

    /**
     * Database Column Remarks:
     *   服务ID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.service_id
     *
     * @mbg.generated
     */
    private String serviceId;

    /**
     * Database Column Remarks:
     *   服务名称
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.service_name
     *
     * @mbg.generated
     */
    private String serviceName;

    /**
     * Database Column Remarks:
     *   行程费用
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.journey_fee
     *
     * @mbg.generated
     */
    private BigDecimal journeyFee;

    /**
     * Database Column Remarks:
     *   服务单价
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.service_price
     *
     * @mbg.generated
     */
    private BigDecimal servicePrice;

    /**
     * Database Column Remarks:
     *   服务份数
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.service_count
     *
     * @mbg.generated
     */
    private Integer serviceCount;

    /**
     * Database Column Remarks:
     *   总计费用
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.total_amount
     *
     * @mbg.generated
     */
    private BigDecimal totalAmount;

    /**
     * Database Column Remarks:
     *   预约服务开始时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.book_start_time
     *
     * @mbg.generated
     */
    private Date bookStartTime;

    /**
     * Database Column Remarks:
     *   预约服务结束时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.book_end_time
     *
     * @mbg.generated
     */
    private Date bookEndTime;

    /**
     * Database Column Remarks:
     *   出发上门时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.departure_time
     *
     * @mbg.generated
     */
    private Date departureTime;

    /**
     * Database Column Remarks:
     *   实际服务开始时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.real_serv_start_time
     *
     * @mbg.generated
     */
    private Date realServStartTime;

    /**
     * Database Column Remarks:
     *   实际服务结束时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.real_serv_done_time
     *
     * @mbg.generated
     */
    private Date realServDoneTime;

    /**
     * Database Column Remarks:
     *   订单取消原因,1-用户取消，2-推拿师取消,3-超时取消
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.cancel_reason
     *
     * @mbg.generated
     */
    private Byte cancelReason;

    /**
     * Database Column Remarks:
     *   订单取消时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.cancel_time
     *
     * @mbg.generated
     */
    private Date cancelTime;

    /**
     * Database Column Remarks:
     *   客户确认时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.customer_confirm_time
     *
     * @mbg.generated
     */
    private Date customerConfirmTime;

    /**
     * Database Column Remarks:
     *   顾问确认时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.provider_confirm_time
     *
     * @mbg.generated
     */
    private Date providerConfirmTime;

    /**
     * Database Column Remarks:
     *   顾问抢单时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.provider_grab_time
     *
     * @mbg.generated
     */
    private Date providerGrabTime;

    /**
     * Database Column Remarks:
     *   到期时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.state_expire_time
     *
     * @mbg.generated
     */
    private Date stateExpireTime;

    /**
     * Database Column Remarks:
     *   创建时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.create_by
     *
     * @mbg.generated
     */
    private String createBy;

    /**
     * Database Column Remarks:
     *   最后修改时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     * Database Column Remarks:
     *   最后修改者
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.update_by
     *
     * @mbg.generated
     */
    private String updateBy;

    /**
     * Database Column Remarks:
     *   版本-乐观锁
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.version
     *
     * @mbg.generated
     */
    private Integer version;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.order_id
     *
     * @return the value of order_info.order_id
     *
     * @mbg.generated
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.order_id
     *
     * @param orderId the value for order_info.order_id
     *
     * @mbg.generated
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.order_no
     *
     * @return the value of order_info.order_no
     *
     * @mbg.generated
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.order_no
     *
     * @param orderNo the value for order_info.order_no
     *
     * @mbg.generated
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.customer_user_id
     *
     * @return the value of order_info.customer_user_id
     *
     * @mbg.generated
     */
    public String getCustomerUserId() {
        return customerUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.customer_user_id
     *
     * @param customerUserId the value for order_info.customer_user_id
     *
     * @mbg.generated
     */
    public void setCustomerUserId(String customerUserId) {
        this.customerUserId = customerUserId == null ? null : customerUserId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.customer_name
     *
     * @return the value of order_info.customer_name
     *
     * @mbg.generated
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.customer_name
     *
     * @param customerName the value for order_info.customer_name
     *
     * @mbg.generated
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.provider_user_id
     *
     * @return the value of order_info.provider_user_id
     *
     * @mbg.generated
     */
    public String getProviderUserId() {
        return providerUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.provider_user_id
     *
     * @param providerUserId the value for order_info.provider_user_id
     *
     * @mbg.generated
     */
    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId == null ? null : providerUserId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.provider_name
     *
     * @return the value of order_info.provider_name
     *
     * @mbg.generated
     */
    public String getProviderName() {
        return providerName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.provider_name
     *
     * @param providerName the value for order_info.provider_name
     *
     * @mbg.generated
     */
    public void setProviderName(String providerName) {
        this.providerName = providerName == null ? null : providerName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.member_id
     *
     * @return the value of order_info.member_id
     *
     * @mbg.generated
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.member_id
     *
     * @param memberId the value for order_info.member_id
     *
     * @mbg.generated
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.latitude
     *
     * @return the value of order_info.latitude
     *
     * @mbg.generated
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.latitude
     *
     * @param latitude the value for order_info.latitude
     *
     * @mbg.generated
     */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.longitude
     *
     * @return the value of order_info.longitude
     *
     * @mbg.generated
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.longitude
     *
     * @param longitude the value for order_info.longitude
     *
     * @mbg.generated
     */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.order_status
     *
     * @return the value of order_info.order_status
     *
     * @mbg.generated
     */
    public Byte getOrderStatus() {
        return orderStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.order_status
     *
     * @param orderStatus the value for order_info.order_status
     *
     * @mbg.generated
     */
    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.city_id
     *
     * @return the value of order_info.city_id
     *
     * @mbg.generated
     */
    public Short getCityId() {
        return cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.city_id
     *
     * @param cityId the value for order_info.city_id
     *
     * @mbg.generated
     */
    public void setCityId(Short cityId) {
        this.cityId = cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.address
     *
     * @return the value of order_info.address
     *
     * @mbg.generated
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.address
     *
     * @param address the value for order_info.address
     *
     * @mbg.generated
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.comment_status
     *
     * @return the value of order_info.comment_status
     *
     * @mbg.generated
     */
    public Byte getCommentStatus() {
        return commentStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.comment_status
     *
     * @param commentStatus the value for order_info.comment_status
     *
     * @mbg.generated
     */
    public void setCommentStatus(Byte commentStatus) {
        this.commentStatus = commentStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.contact_phone
     *
     * @return the value of order_info.contact_phone
     *
     * @mbg.generated
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.contact_phone
     *
     * @param contactPhone the value for order_info.contact_phone
     *
     * @mbg.generated
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.contact_man
     *
     * @return the value of order_info.contact_man
     *
     * @mbg.generated
     */
    public String getContactMan() {
        return contactMan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.contact_man
     *
     * @param contactMan the value for order_info.contact_man
     *
     * @mbg.generated
     */
    public void setContactMan(String contactMan) {
        this.contactMan = contactMan == null ? null : contactMan.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.service_id
     *
     * @return the value of order_info.service_id
     *
     * @mbg.generated
     */
    public String getServiceId() {
        return serviceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.service_id
     *
     * @param serviceId the value for order_info.service_id
     *
     * @mbg.generated
     */
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId == null ? null : serviceId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.service_name
     *
     * @return the value of order_info.service_name
     *
     * @mbg.generated
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.service_name
     *
     * @param serviceName the value for order_info.service_name
     *
     * @mbg.generated
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName == null ? null : serviceName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.journey_fee
     *
     * @return the value of order_info.journey_fee
     *
     * @mbg.generated
     */
    public BigDecimal getJourneyFee() {
        return journeyFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.journey_fee
     *
     * @param journeyFee the value for order_info.journey_fee
     *
     * @mbg.generated
     */
    public void setJourneyFee(BigDecimal journeyFee) {
        this.journeyFee = journeyFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.service_price
     *
     * @return the value of order_info.service_price
     *
     * @mbg.generated
     */
    public BigDecimal getServicePrice() {
        return servicePrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.service_price
     *
     * @param servicePrice the value for order_info.service_price
     *
     * @mbg.generated
     */
    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.service_count
     *
     * @return the value of order_info.service_count
     *
     * @mbg.generated
     */
    public Integer getServiceCount() {
        return serviceCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.service_count
     *
     * @param serviceCount the value for order_info.service_count
     *
     * @mbg.generated
     */
    public void setServiceCount(Integer serviceCount) {
        this.serviceCount = serviceCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.total_amount
     *
     * @return the value of order_info.total_amount
     *
     * @mbg.generated
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.total_amount
     *
     * @param totalAmount the value for order_info.total_amount
     *
     * @mbg.generated
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.book_start_time
     *
     * @return the value of order_info.book_start_time
     *
     * @mbg.generated
     */
    public Date getBookStartTime() {
        return bookStartTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.book_start_time
     *
     * @param bookStartTime the value for order_info.book_start_time
     *
     * @mbg.generated
     */
    public void setBookStartTime(Date bookStartTime) {
        this.bookStartTime = bookStartTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.book_end_time
     *
     * @return the value of order_info.book_end_time
     *
     * @mbg.generated
     */
    public Date getBookEndTime() {
        return bookEndTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.book_end_time
     *
     * @param bookEndTime the value for order_info.book_end_time
     *
     * @mbg.generated
     */
    public void setBookEndTime(Date bookEndTime) {
        this.bookEndTime = bookEndTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.departure_time
     *
     * @return the value of order_info.departure_time
     *
     * @mbg.generated
     */
    public Date getDepartureTime() {
        return departureTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.departure_time
     *
     * @param departureTime the value for order_info.departure_time
     *
     * @mbg.generated
     */
    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.real_serv_start_time
     *
     * @return the value of order_info.real_serv_start_time
     *
     * @mbg.generated
     */
    public Date getRealServStartTime() {
        return realServStartTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.real_serv_start_time
     *
     * @param realServStartTime the value for order_info.real_serv_start_time
     *
     * @mbg.generated
     */
    public void setRealServStartTime(Date realServStartTime) {
        this.realServStartTime = realServStartTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.real_serv_done_time
     *
     * @return the value of order_info.real_serv_done_time
     *
     * @mbg.generated
     */
    public Date getRealServDoneTime() {
        return realServDoneTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.real_serv_done_time
     *
     * @param realServDoneTime the value for order_info.real_serv_done_time
     *
     * @mbg.generated
     */
    public void setRealServDoneTime(Date realServDoneTime) {
        this.realServDoneTime = realServDoneTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.cancel_reason
     *
     * @return the value of order_info.cancel_reason
     *
     * @mbg.generated
     */
    public Byte getCancelReason() {
        return cancelReason;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.cancel_reason
     *
     * @param cancelReason the value for order_info.cancel_reason
     *
     * @mbg.generated
     */
    public void setCancelReason(Byte cancelReason) {
        this.cancelReason = cancelReason;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.cancel_time
     *
     * @return the value of order_info.cancel_time
     *
     * @mbg.generated
     */
    public Date getCancelTime() {
        return cancelTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.cancel_time
     *
     * @param cancelTime the value for order_info.cancel_time
     *
     * @mbg.generated
     */
    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.customer_confirm_time
     *
     * @return the value of order_info.customer_confirm_time
     *
     * @mbg.generated
     */
    public Date getCustomerConfirmTime() {
        return customerConfirmTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.customer_confirm_time
     *
     * @param customerConfirmTime the value for order_info.customer_confirm_time
     *
     * @mbg.generated
     */
    public void setCustomerConfirmTime(Date customerConfirmTime) {
        this.customerConfirmTime = customerConfirmTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.provider_confirm_time
     *
     * @return the value of order_info.provider_confirm_time
     *
     * @mbg.generated
     */
    public Date getProviderConfirmTime() {
        return providerConfirmTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.provider_confirm_time
     *
     * @param providerConfirmTime the value for order_info.provider_confirm_time
     *
     * @mbg.generated
     */
    public void setProviderConfirmTime(Date providerConfirmTime) {
        this.providerConfirmTime = providerConfirmTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.provider_grab_time
     *
     * @return the value of order_info.provider_grab_time
     *
     * @mbg.generated
     */
    public Date getProviderGrabTime() {
        return providerGrabTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.provider_grab_time
     *
     * @param providerGrabTime the value for order_info.provider_grab_time
     *
     * @mbg.generated
     */
    public void setProviderGrabTime(Date providerGrabTime) {
        this.providerGrabTime = providerGrabTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.state_expire_time
     *
     * @return the value of order_info.state_expire_time
     *
     * @mbg.generated
     */
    public Date getStateExpireTime() {
        return stateExpireTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.state_expire_time
     *
     * @param stateExpireTime the value for order_info.state_expire_time
     *
     * @mbg.generated
     */
    public void setStateExpireTime(Date stateExpireTime) {
        this.stateExpireTime = stateExpireTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.create_time
     *
     * @return the value of order_info.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.create_time
     *
     * @param createTime the value for order_info.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.create_by
     *
     * @return the value of order_info.create_by
     *
     * @mbg.generated
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.create_by
     *
     * @param createBy the value for order_info.create_by
     *
     * @mbg.generated
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.update_time
     *
     * @return the value of order_info.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.update_time
     *
     * @param updateTime the value for order_info.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.update_by
     *
     * @return the value of order_info.update_by
     *
     * @mbg.generated
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.update_by
     *
     * @param updateBy the value for order_info.update_by
     *
     * @mbg.generated
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.version
     *
     * @return the value of order_info.version
     *
     * @mbg.generated
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.version
     *
     * @param version the value for order_info.version
     *
     * @mbg.generated
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
}