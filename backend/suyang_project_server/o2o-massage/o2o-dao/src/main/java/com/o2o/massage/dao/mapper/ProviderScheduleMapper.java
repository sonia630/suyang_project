package com.o2o.massage.dao.mapper;

import com.o2o.massage.dao.entity.ProviderSchedule;
import com.o2o.massage.dao.entity.ProviderScheduleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProviderScheduleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table provider_schedule
     *
     * @mbg.generated
     */
    long countByExample(ProviderScheduleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table provider_schedule
     *
     * @mbg.generated
     */
    int insert(ProviderSchedule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table provider_schedule
     *
     * @mbg.generated
     */
    int insertSelective(ProviderSchedule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table provider_schedule
     *
     * @mbg.generated
     */
    List<ProviderSchedule> selectByExample(ProviderScheduleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table provider_schedule
     *
     * @mbg.generated
     */
    ProviderSchedule selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table provider_schedule
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") ProviderSchedule record, @Param("example") ProviderScheduleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table provider_schedule
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") ProviderSchedule record, @Param("example") ProviderScheduleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table provider_schedule
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ProviderSchedule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table provider_schedule
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ProviderSchedule record);
}