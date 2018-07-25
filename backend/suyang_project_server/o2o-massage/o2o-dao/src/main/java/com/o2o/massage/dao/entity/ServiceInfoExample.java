package com.o2o.massage.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceInfoExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table service_info
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table service_info
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table service_info
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table service_info
     *
     * @mbg.generated
     */
    public ServiceInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table service_info
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table service_info
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table service_info
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table service_info
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table service_info
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table service_info
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table service_info
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table service_info
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table service_info
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table service_info
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table service_info
     *
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andServiceIdIsNull() {
            addCriterion("`service_id` is null");
            return (Criteria) this;
        }

        public Criteria andServiceIdIsNotNull() {
            addCriterion("`service_id` is not null");
            return (Criteria) this;
        }

        public Criteria andServiceIdEqualTo(String value) {
            addCriterion("`service_id` =", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdNotEqualTo(String value) {
            addCriterion("`service_id` <>", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdGreaterThan(String value) {
            addCriterion("`service_id` >", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdGreaterThanOrEqualTo(String value) {
            addCriterion("`service_id` >=", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdLessThan(String value) {
            addCriterion("`service_id` <", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdLessThanOrEqualTo(String value) {
            addCriterion("`service_id` <=", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdLike(String value) {
            addCriterion("`service_id` like", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdNotLike(String value) {
            addCriterion("`service_id` not like", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdIn(List<String> values) {
            addCriterion("`service_id` in", values, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdNotIn(List<String> values) {
            addCriterion("`service_id` not in", values, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdBetween(String value1, String value2) {
            addCriterion("`service_id` between", value1, value2, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdNotBetween(String value1, String value2) {
            addCriterion("`service_id` not between", value1, value2, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceNameIsNull() {
            addCriterion("`service_name` is null");
            return (Criteria) this;
        }

        public Criteria andServiceNameIsNotNull() {
            addCriterion("`service_name` is not null");
            return (Criteria) this;
        }

        public Criteria andServiceNameEqualTo(String value) {
            addCriterion("`service_name` =", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotEqualTo(String value) {
            addCriterion("`service_name` <>", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameGreaterThan(String value) {
            addCriterion("`service_name` >", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameGreaterThanOrEqualTo(String value) {
            addCriterion("`service_name` >=", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameLessThan(String value) {
            addCriterion("`service_name` <", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameLessThanOrEqualTo(String value) {
            addCriterion("`service_name` <=", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameLike(String value) {
            addCriterion("`service_name` like", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotLike(String value) {
            addCriterion("`service_name` not like", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameIn(List<String> values) {
            addCriterion("`service_name` in", values, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotIn(List<String> values) {
            addCriterion("`service_name` not in", values, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameBetween(String value1, String value2) {
            addCriterion("`service_name` between", value1, value2, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotBetween(String value1, String value2) {
            addCriterion("`service_name` not between", value1, value2, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceSummaryIsNull() {
            addCriterion("`service_summary` is null");
            return (Criteria) this;
        }

        public Criteria andServiceSummaryIsNotNull() {
            addCriterion("`service_summary` is not null");
            return (Criteria) this;
        }

        public Criteria andServiceSummaryEqualTo(String value) {
            addCriterion("`service_summary` =", value, "serviceSummary");
            return (Criteria) this;
        }

        public Criteria andServiceSummaryNotEqualTo(String value) {
            addCriterion("`service_summary` <>", value, "serviceSummary");
            return (Criteria) this;
        }

        public Criteria andServiceSummaryGreaterThan(String value) {
            addCriterion("`service_summary` >", value, "serviceSummary");
            return (Criteria) this;
        }

        public Criteria andServiceSummaryGreaterThanOrEqualTo(String value) {
            addCriterion("`service_summary` >=", value, "serviceSummary");
            return (Criteria) this;
        }

        public Criteria andServiceSummaryLessThan(String value) {
            addCriterion("`service_summary` <", value, "serviceSummary");
            return (Criteria) this;
        }

        public Criteria andServiceSummaryLessThanOrEqualTo(String value) {
            addCriterion("`service_summary` <=", value, "serviceSummary");
            return (Criteria) this;
        }

        public Criteria andServiceSummaryLike(String value) {
            addCriterion("`service_summary` like", value, "serviceSummary");
            return (Criteria) this;
        }

        public Criteria andServiceSummaryNotLike(String value) {
            addCriterion("`service_summary` not like", value, "serviceSummary");
            return (Criteria) this;
        }

        public Criteria andServiceSummaryIn(List<String> values) {
            addCriterion("`service_summary` in", values, "serviceSummary");
            return (Criteria) this;
        }

        public Criteria andServiceSummaryNotIn(List<String> values) {
            addCriterion("`service_summary` not in", values, "serviceSummary");
            return (Criteria) this;
        }

        public Criteria andServiceSummaryBetween(String value1, String value2) {
            addCriterion("`service_summary` between", value1, value2, "serviceSummary");
            return (Criteria) this;
        }

        public Criteria andServiceSummaryNotBetween(String value1, String value2) {
            addCriterion("`service_summary` not between", value1, value2, "serviceSummary");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("`description` is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("`description` is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("`description` =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("`description` <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("`description` >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("`description` >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("`description` <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("`description` <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("`description` like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("`description` not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("`description` in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("`description` not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("`description` between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("`description` not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andSortOrderIsNull() {
            addCriterion("`sort_order` is null");
            return (Criteria) this;
        }

        public Criteria andSortOrderIsNotNull() {
            addCriterion("`sort_order` is not null");
            return (Criteria) this;
        }

        public Criteria andSortOrderEqualTo(Integer value) {
            addCriterion("`sort_order` =", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotEqualTo(Integer value) {
            addCriterion("`sort_order` <>", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderGreaterThan(Integer value) {
            addCriterion("`sort_order` >", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("`sort_order` >=", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderLessThan(Integer value) {
            addCriterion("`sort_order` <", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderLessThanOrEqualTo(Integer value) {
            addCriterion("`sort_order` <=", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderIn(List<Integer> values) {
            addCriterion("`sort_order` in", values, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotIn(List<Integer> values) {
            addCriterion("`sort_order` not in", values, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderBetween(Integer value1, Integer value2) {
            addCriterion("`sort_order` between", value1, value2, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("`sort_order` not between", value1, value2, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("`create_time` is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("`create_time` is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("`create_time` =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("`create_time` <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("`create_time` >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("`create_time` >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("`create_time` <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("`create_time` <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("`create_time` in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("`create_time` not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("`create_time` between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("`create_time` not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNull() {
            addCriterion("`create_by` is null");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNotNull() {
            addCriterion("`create_by` is not null");
            return (Criteria) this;
        }

        public Criteria andCreateByEqualTo(String value) {
            addCriterion("`create_by` =", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotEqualTo(String value) {
            addCriterion("`create_by` <>", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThan(String value) {
            addCriterion("`create_by` >", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThanOrEqualTo(String value) {
            addCriterion("`create_by` >=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThan(String value) {
            addCriterion("`create_by` <", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThanOrEqualTo(String value) {
            addCriterion("`create_by` <=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLike(String value) {
            addCriterion("`create_by` like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotLike(String value) {
            addCriterion("`create_by` not like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByIn(List<String> values) {
            addCriterion("`create_by` in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotIn(List<String> values) {
            addCriterion("`create_by` not in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByBetween(String value1, String value2) {
            addCriterion("`create_by` between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotBetween(String value1, String value2) {
            addCriterion("`create_by` not between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("`update_time` is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("`update_time` is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("`update_time` =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("`update_time` <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("`update_time` >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("`update_time` >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("`update_time` <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("`update_time` <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("`update_time` in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("`update_time` not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("`update_time` between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("`update_time` not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNull() {
            addCriterion("`update_by` is null");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNotNull() {
            addCriterion("`update_by` is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateByEqualTo(String value) {
            addCriterion("`update_by` =", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotEqualTo(String value) {
            addCriterion("`update_by` <>", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThan(String value) {
            addCriterion("`update_by` >", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThanOrEqualTo(String value) {
            addCriterion("`update_by` >=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThan(String value) {
            addCriterion("`update_by` <", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThanOrEqualTo(String value) {
            addCriterion("`update_by` <=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLike(String value) {
            addCriterion("`update_by` like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotLike(String value) {
            addCriterion("`update_by` not like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByIn(List<String> values) {
            addCriterion("`update_by` in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotIn(List<String> values) {
            addCriterion("`update_by` not in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByBetween(String value1, String value2) {
            addCriterion("`update_by` between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotBetween(String value1, String value2) {
            addCriterion("`update_by` not between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNull() {
            addCriterion("`remarks` is null");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNotNull() {
            addCriterion("`remarks` is not null");
            return (Criteria) this;
        }

        public Criteria andRemarksEqualTo(String value) {
            addCriterion("`remarks` =", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotEqualTo(String value) {
            addCriterion("`remarks` <>", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThan(String value) {
            addCriterion("`remarks` >", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("`remarks` >=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThan(String value) {
            addCriterion("`remarks` <", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThanOrEqualTo(String value) {
            addCriterion("`remarks` <=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLike(String value) {
            addCriterion("`remarks` like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotLike(String value) {
            addCriterion("`remarks` not like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksIn(List<String> values) {
            addCriterion("`remarks` in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotIn(List<String> values) {
            addCriterion("`remarks` not in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksBetween(String value1, String value2) {
            addCriterion("`remarks` between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotBetween(String value1, String value2) {
            addCriterion("`remarks` not between", value1, value2, "remarks");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table service_info
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table service_info
     *
     * @mbg.generated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}