package com.o2o.massage.dao.entity;

import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table verify_code_info
 *
 * @mbg.generated do_not_delete_during_merge
 */
public class VerifyCodeInfo {
    /**
     * Database Column Remarks:
     *   自增ID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column verify_code_info.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     * Database Column Remarks:
     *   手机号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column verify_code_info.phone_no
     *
     * @mbg.generated
     */
    private String phoneNo;

    /**
     * Database Column Remarks:
     *   4位验证码
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column verify_code_info.verify_code
     *
     * @mbg.generated
     */
    private Short verifyCode;

    /**
     * Database Column Remarks:
     *   创建时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column verify_code_info.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * Database Column Remarks:
     *   最后修改时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column verify_code_info.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     * Database Column Remarks:
     *   状态，0-未使用,1-已使用
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column verify_code_info.code_state
     *
     * @mbg.generated
     */
    private Byte codeState;

    /**
     * Database Column Remarks:
     *   1-注册,2-忘记密码,3-更换手机号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column verify_code_info.scenario
     *
     * @mbg.generated
     */
    private Byte scenario;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column verify_code_info.id
     *
     * @return the value of verify_code_info.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column verify_code_info.id
     *
     * @param id the value for verify_code_info.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column verify_code_info.phone_no
     *
     * @return the value of verify_code_info.phone_no
     *
     * @mbg.generated
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column verify_code_info.phone_no
     *
     * @param phoneNo the value for verify_code_info.phone_no
     *
     * @mbg.generated
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo == null ? null : phoneNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column verify_code_info.verify_code
     *
     * @return the value of verify_code_info.verify_code
     *
     * @mbg.generated
     */
    public Short getVerifyCode() {
        return verifyCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column verify_code_info.verify_code
     *
     * @param verifyCode the value for verify_code_info.verify_code
     *
     * @mbg.generated
     */
    public void setVerifyCode(Short verifyCode) {
        this.verifyCode = verifyCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column verify_code_info.create_time
     *
     * @return the value of verify_code_info.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column verify_code_info.create_time
     *
     * @param createTime the value for verify_code_info.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column verify_code_info.update_time
     *
     * @return the value of verify_code_info.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column verify_code_info.update_time
     *
     * @param updateTime the value for verify_code_info.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column verify_code_info.code_state
     *
     * @return the value of verify_code_info.code_state
     *
     * @mbg.generated
     */
    public Byte getCodeState() {
        return codeState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column verify_code_info.code_state
     *
     * @param codeState the value for verify_code_info.code_state
     *
     * @mbg.generated
     */
    public void setCodeState(Byte codeState) {
        this.codeState = codeState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column verify_code_info.scenario
     *
     * @return the value of verify_code_info.scenario
     *
     * @mbg.generated
     */
    public Byte getScenario() {
        return scenario;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column verify_code_info.scenario
     *
     * @param scenario the value for verify_code_info.scenario
     *
     * @mbg.generated
     */
    public void setScenario(Byte scenario) {
        this.scenario = scenario;
    }
}