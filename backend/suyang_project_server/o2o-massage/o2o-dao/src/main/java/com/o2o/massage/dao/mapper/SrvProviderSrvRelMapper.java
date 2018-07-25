package com.o2o.massage.dao.mapper;

import com.o2o.massage.dao.entity.SrvProviderSrvRel;
import com.o2o.massage.dao.entity.SrvProviderSrvRelExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SrvProviderSrvRelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table srv_provider_srv_rel
     *
     * @mbg.generated
     */
    long countByExample(SrvProviderSrvRelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table srv_provider_srv_rel
     *
     * @mbg.generated
     */
    int insert(SrvProviderSrvRel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table srv_provider_srv_rel
     *
     * @mbg.generated
     */
    int insertSelective(SrvProviderSrvRel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table srv_provider_srv_rel
     *
     * @mbg.generated
     */
    List<SrvProviderSrvRel> selectByExample(SrvProviderSrvRelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table srv_provider_srv_rel
     *
     * @mbg.generated
     */
    SrvProviderSrvRel selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table srv_provider_srv_rel
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") SrvProviderSrvRel record, @Param("example") SrvProviderSrvRelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table srv_provider_srv_rel
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") SrvProviderSrvRel record, @Param("example") SrvProviderSrvRelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table srv_provider_srv_rel
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SrvProviderSrvRel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table srv_provider_srv_rel
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SrvProviderSrvRel record);
}