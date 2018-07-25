package com.o2o.massage.dao.entity;

import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table customer_info
 *
 * @mbg.generated do_not_delete_during_merge
 */
public class CustomerInfo {
    /**
     * Database Column Remarks:
     *   客户Id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer_info.customer_id
     *
     * @mbg.generated
     */
    private Long customerId;

    /**
     * Database Column Remarks:
     *   系统用户Id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer_info.customer_user_id
     *
     * @mbg.generated
     */
    private String customerUserId;

    /**
     * Database Column Remarks:
     *   客户来源
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer_info.customer_source
     *
     * @mbg.generated
     */
    private String customerSource;

    /**
     * Database Column Remarks:
     *   创建时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer_info.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * Database Column Remarks:
     *   最后修改时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer_info.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     * Database Column Remarks:
     *   创建人
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer_info.create_by
     *
     * @mbg.generated
     */
    private String createBy;

    /**
     * Database Column Remarks:
     *   更新人
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer_info.update_by
     *
     * @mbg.generated
     */
    private String updateBy;

    /**
     * Database Column Remarks:
     *   客户状态
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer_info.status
     *
     * @mbg.generated
     */
    private Byte status;

    /**
     * Database Column Remarks:
     *   备注说明
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer_info.remarks
     *
     * @mbg.generated
     */
    private String remarks;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer_info.customer_id
     *
     * @return the value of customer_info.customer_id
     *
     * @mbg.generated
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer_info.customer_id
     *
     * @param customerId the value for customer_info.customer_id
     *
     * @mbg.generated
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer_info.customer_user_id
     *
     * @return the value of customer_info.customer_user_id
     *
     * @mbg.generated
     */
    public String getCustomerUserId() {
        return customerUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer_info.customer_user_id
     *
     * @param customerUserId the value for customer_info.customer_user_id
     *
     * @mbg.generated
     */
    public void setCustomerUserId(String customerUserId) {
        this.customerUserId = customerUserId == null ? null : customerUserId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer_info.customer_source
     *
     * @return the value of customer_info.customer_source
     *
     * @mbg.generated
     */
    public String getCustomerSource() {
        return customerSource;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer_info.customer_source
     *
     * @param customerSource the value for customer_info.customer_source
     *
     * @mbg.generated
     */
    public void setCustomerSource(String customerSource) {
        this.customerSource = customerSource == null ? null : customerSource.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer_info.create_time
     *
     * @return the value of customer_info.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer_info.create_time
     *
     * @param createTime the value for customer_info.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer_info.update_time
     *
     * @return the value of customer_info.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer_info.update_time
     *
     * @param updateTime the value for customer_info.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer_info.create_by
     *
     * @return the value of customer_info.create_by
     *
     * @mbg.generated
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer_info.create_by
     *
     * @param createBy the value for customer_info.create_by
     *
     * @mbg.generated
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer_info.update_by
     *
     * @return the value of customer_info.update_by
     *
     * @mbg.generated
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer_info.update_by
     *
     * @param updateBy the value for customer_info.update_by
     *
     * @mbg.generated
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer_info.status
     *
     * @return the value of customer_info.status
     *
     * @mbg.generated
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer_info.status
     *
     * @param status the value for customer_info.status
     *
     * @mbg.generated
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer_info.remarks
     *
     * @return the value of customer_info.remarks
     *
     * @mbg.generated
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer_info.remarks
     *
     * @param remarks the value for customer_info.remarks
     *
     * @mbg.generated
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}