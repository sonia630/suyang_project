package com.o2o.massage.dao.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table evaluation_info
 *
 * @mbg.generated do_not_delete_during_merge
 */
public class EvaluationInfo {
    /**
     * Database Column Remarks:
     *   评价ID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluation_info.evaluation_id
     *
     * @mbg.generated
     */
    private Long evaluationId;

    /**
     * Database Column Remarks:
     *   服务提供者ID
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluation_info.provider_user_id
     *
     * @mbg.generated
     */
    private String providerUserId;

    /**
     * Database Column Remarks:
     *   订单编号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluation_info.order_no
     *
     * @mbg.generated
     */
    private String orderNo;

    /**
     * Database Column Remarks:
     *   评价内容
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluation_info.evalucation_desc
     *
     * @mbg.generated
     */
    private String evalucationDesc;

    /**
     * Database Column Remarks:
     *   评价分数
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluation_info.evaluation_score
     *
     * @mbg.generated
     */
    private BigDecimal evaluationScore;

    /**
     * Database Column Remarks:
     *   创建时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluation_info.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * Database Column Remarks:
     *   最后修改时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluation_info.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     * Database Column Remarks:
     *   创建人
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluation_info.create_by
     *
     * @mbg.generated
     */
    private String createBy;

    /**
     * Database Column Remarks:
     *   更新人
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluation_info.update_by
     *
     * @mbg.generated
     */
    private String updateBy;

    /**
     * Database Column Remarks:
     *   点评状态
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluation_info.status
     *
     * @mbg.generated
     */
    private Byte status;

    /**
     * Database Column Remarks:
     *   备注说明
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluation_info.remarks
     *
     * @mbg.generated
     */
    private String remarks;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluation_info.evaluation_id
     *
     * @return the value of evaluation_info.evaluation_id
     *
     * @mbg.generated
     */
    public Long getEvaluationId() {
        return evaluationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluation_info.evaluation_id
     *
     * @param evaluationId the value for evaluation_info.evaluation_id
     *
     * @mbg.generated
     */
    public void setEvaluationId(Long evaluationId) {
        this.evaluationId = evaluationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluation_info.provider_user_id
     *
     * @return the value of evaluation_info.provider_user_id
     *
     * @mbg.generated
     */
    public String getProviderUserId() {
        return providerUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluation_info.provider_user_id
     *
     * @param providerUserId the value for evaluation_info.provider_user_id
     *
     * @mbg.generated
     */
    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId == null ? null : providerUserId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluation_info.order_no
     *
     * @return the value of evaluation_info.order_no
     *
     * @mbg.generated
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluation_info.order_no
     *
     * @param orderNo the value for evaluation_info.order_no
     *
     * @mbg.generated
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluation_info.evalucation_desc
     *
     * @return the value of evaluation_info.evalucation_desc
     *
     * @mbg.generated
     */
    public String getEvalucationDesc() {
        return evalucationDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluation_info.evalucation_desc
     *
     * @param evalucationDesc the value for evaluation_info.evalucation_desc
     *
     * @mbg.generated
     */
    public void setEvalucationDesc(String evalucationDesc) {
        this.evalucationDesc = evalucationDesc == null ? null : evalucationDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluation_info.evaluation_score
     *
     * @return the value of evaluation_info.evaluation_score
     *
     * @mbg.generated
     */
    public BigDecimal getEvaluationScore() {
        return evaluationScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluation_info.evaluation_score
     *
     * @param evaluationScore the value for evaluation_info.evaluation_score
     *
     * @mbg.generated
     */
    public void setEvaluationScore(BigDecimal evaluationScore) {
        this.evaluationScore = evaluationScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluation_info.create_time
     *
     * @return the value of evaluation_info.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluation_info.create_time
     *
     * @param createTime the value for evaluation_info.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluation_info.update_time
     *
     * @return the value of evaluation_info.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluation_info.update_time
     *
     * @param updateTime the value for evaluation_info.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluation_info.create_by
     *
     * @return the value of evaluation_info.create_by
     *
     * @mbg.generated
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluation_info.create_by
     *
     * @param createBy the value for evaluation_info.create_by
     *
     * @mbg.generated
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluation_info.update_by
     *
     * @return the value of evaluation_info.update_by
     *
     * @mbg.generated
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluation_info.update_by
     *
     * @param updateBy the value for evaluation_info.update_by
     *
     * @mbg.generated
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluation_info.status
     *
     * @return the value of evaluation_info.status
     *
     * @mbg.generated
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluation_info.status
     *
     * @param status the value for evaluation_info.status
     *
     * @mbg.generated
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluation_info.remarks
     *
     * @return the value of evaluation_info.remarks
     *
     * @mbg.generated
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluation_info.remarks
     *
     * @param remarks the value for evaluation_info.remarks
     *
     * @mbg.generated
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}