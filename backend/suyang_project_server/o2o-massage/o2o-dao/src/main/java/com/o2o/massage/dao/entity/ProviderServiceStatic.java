package com.o2o.massage.dao.entity;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table provider_service_static
 *
 * @mbg.generated do_not_delete_during_merge
 */
public class ProviderServiceStatic {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column provider_service_static.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column provider_service_static.provider_user_id
     *
     * @mbg.generated
     */
    private String providerUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column provider_service_static.service_times
     *
     * @mbg.generated
     */
    private Integer serviceTimes;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column provider_service_static.service_id
     *
     * @mbg.generated
     */
    private String serviceId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column provider_service_static.id
     *
     * @return the value of provider_service_static.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column provider_service_static.id
     *
     * @param id the value for provider_service_static.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column provider_service_static.provider_user_id
     *
     * @return the value of provider_service_static.provider_user_id
     *
     * @mbg.generated
     */
    public String getProviderUserId() {
        return providerUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column provider_service_static.provider_user_id
     *
     * @param providerUserId the value for provider_service_static.provider_user_id
     *
     * @mbg.generated
     */
    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId == null ? null : providerUserId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column provider_service_static.service_times
     *
     * @return the value of provider_service_static.service_times
     *
     * @mbg.generated
     */
    public Integer getServiceTimes() {
        return serviceTimes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column provider_service_static.service_times
     *
     * @param serviceTimes the value for provider_service_static.service_times
     *
     * @mbg.generated
     */
    public void setServiceTimes(Integer serviceTimes) {
        this.serviceTimes = serviceTimes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column provider_service_static.service_id
     *
     * @return the value of provider_service_static.service_id
     *
     * @mbg.generated
     */
    public String getServiceId() {
        return serviceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column provider_service_static.service_id
     *
     * @param serviceId the value for provider_service_static.service_id
     *
     * @mbg.generated
     */
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId == null ? null : serviceId.trim();
    }
}