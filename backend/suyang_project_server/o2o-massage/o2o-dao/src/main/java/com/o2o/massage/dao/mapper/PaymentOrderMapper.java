package com.o2o.massage.dao.mapper;

import com.o2o.massage.dao.entity.PaymentOrder;
import com.o2o.massage.dao.entity.PaymentOrderExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PaymentOrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_order
     *
     * @mbg.generated
     */
    long countByExample(PaymentOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_order
     *
     * @mbg.generated
     */
    int insert(PaymentOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_order
     *
     * @mbg.generated
     */
    int insertSelective(PaymentOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_order
     *
     * @mbg.generated
     */
    List<PaymentOrder> selectByExample(PaymentOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_order
     *
     * @mbg.generated
     */
    PaymentOrder selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_order
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") PaymentOrder record, @Param("example") PaymentOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_order
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") PaymentOrder record, @Param("example") PaymentOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_order
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PaymentOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table payment_order
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PaymentOrder record);
}