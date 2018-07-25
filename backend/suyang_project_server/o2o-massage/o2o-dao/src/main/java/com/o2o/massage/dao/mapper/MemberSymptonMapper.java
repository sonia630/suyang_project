package com.o2o.massage.dao.mapper;

import com.o2o.massage.dao.entity.MemberSympton;
import com.o2o.massage.dao.entity.MemberSymptonExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberSymptonMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_sympton
     *
     * @mbg.generated
     */
    long countByExample(MemberSymptonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_sympton
     *
     * @mbg.generated
     */
    int insert(MemberSympton record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_sympton
     *
     * @mbg.generated
     */
    int insertSelective(MemberSympton record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_sympton
     *
     * @mbg.generated
     */
    List<MemberSympton> selectByExample(MemberSymptonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_sympton
     *
     * @mbg.generated
     */
    MemberSympton selectByPrimaryKey(Long symptonId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_sympton
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") MemberSympton record, @Param("example") MemberSymptonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_sympton
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") MemberSympton record, @Param("example") MemberSymptonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_sympton
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(MemberSympton record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_sympton
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MemberSympton record);
}