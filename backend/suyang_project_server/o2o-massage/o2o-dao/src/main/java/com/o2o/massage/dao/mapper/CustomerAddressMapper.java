package com.o2o.massage.dao.mapper;

import com.o2o.massage.dao.entity.CustomerAddress;
import com.o2o.massage.dao.entity.CustomerAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CustomerAddressMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_address
     *
     * @mbg.generated
     */
    long countByExample(CustomerAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_address
     *
     * @mbg.generated
     */
    int insert(CustomerAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_address
     *
     * @mbg.generated
     */
    int insertSelective(CustomerAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_address
     *
     * @mbg.generated
     */
    List<CustomerAddress> selectByExample(CustomerAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_address
     *
     * @mbg.generated
     */
    CustomerAddress selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_address
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") CustomerAddress record, @Param("example") CustomerAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_address
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") CustomerAddress record, @Param("example") CustomerAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_address
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(CustomerAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_address
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(CustomerAddress record);
}