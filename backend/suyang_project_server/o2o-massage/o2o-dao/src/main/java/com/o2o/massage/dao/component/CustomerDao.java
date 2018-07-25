package com.o2o.massage.dao.component;

import com.o2o.massage.dao.entity.*;
import com.o2o.massage.dao.mapper.CustomerAddressMapper;
import com.o2o.massage.dao.mapper.CustomerInfoMapper;
import com.o2o.massage.dao.mapper.FamilyMemberMapper;
import com.o2o.massage.dao.mapper.MemberSymptonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: zhongli
 * @Date: 2018/2/25 21:22
 * @Description:
 */
@Component
public class CustomerDao {

    @Autowired
    private CustomerInfoMapper customerInfoMapper;
    @Autowired
    private CustomerAddressMapper customerAddressMapper;
    @Autowired
    private FamilyMemberMapper familyMemberMapper;
    @Autowired
    private MemberSymptonMapper memberSymptonMapper;

    public int insertCustomer(CustomerInfo customerInfo) {
        return customerInfoMapper.insertSelective(customerInfo);
    }

    public int updateCustomer(CustomerInfo record) {
        return customerInfoMapper.updateByPrimaryKeySelective(record);
    }

    public CustomerInfo findOneByUserId(String userId) {
        CustomerInfoExample example = new CustomerInfoExample();
        example.createCriteria().andCustomerUserIdEqualTo(userId);
        List<CustomerInfo> infoList = customerInfoMapper.selectByExample(example);
        if (infoList != null && infoList.size() > 0) {
            return infoList.get(0);
        }
        return null;
    }

    public List<CustomerAddress> getUserLocations(String userId) {
        CustomerAddressExample example = new CustomerAddressExample();
        example.createCriteria().andUseridEqualTo(userId);
        return customerAddressMapper.selectByExample(example);
    }

    public void saveUserLocation(CustomerAddress customerAddress) {
        customerAddressMapper.insertSelective(customerAddress);
    }

    public boolean containAddress(CustomerAddress customerAddress) {
        CustomerAddressExample customerAddressExample = new CustomerAddressExample();
        customerAddressExample.createCriteria().andLatitudeEqualTo(customerAddress.getLatitude())
                .andLongitudeEqualTo(customerAddress.getLongitude())
                .andDetailAddressEqualTo(customerAddress.getDetailAddress());
        return customerAddressMapper.countByExample(customerAddressExample) > 0;
    }

    public void updateUserLocation(CustomerAddress customerAddress) {
        customerAddressMapper.updateByPrimaryKeySelective(customerAddress);
    }

    public List<FamilyMember> getFamilyMembers(String userId) {
        FamilyMemberExample familyMember = new FamilyMemberExample();
        familyMember.createCriteria().andCustomerUserIdEqualTo(userId);
        return familyMemberMapper.selectByExample(familyMember);
    }

    public void saveMember(FamilyMember familyMember) {
        familyMemberMapper.insertSelective(familyMember);
    }

    public FamilyMember getFamilyMember(String memberId) {
        return familyMemberMapper.selectByPrimaryKey(memberId);
    }

    public void saveSympton(MemberSympton memberSympton) {
        memberSymptonMapper.insertSelective(memberSympton);
    }

    public void updateMemberSympton(String orderNo, String userId, String memberId) {
        MemberSympton memberSympton = new MemberSympton();
        memberSympton.setOrderNo(orderNo);

        MemberSymptonExample memberSymptonExample = new MemberSymptonExample();
        memberSymptonExample.createCriteria().andCustomerUserIdEqualTo(userId)
                .andMemberIdEqualTo(memberId);

        memberSymptonMapper.updateByExampleSelective(memberSympton, memberSymptonExample);

    }

    public void updateUserLocation(String userId, CustomerAddress address) {
        CustomerAddressExample addressExample = new CustomerAddressExample();
        addressExample.createCriteria().andUseridEqualTo(userId);
        customerAddressMapper.updateByExampleSelective(address, addressExample);
    }

    public CustomerAddress getUserDefaultLocation(String userId) {
        CustomerAddressExample example = new CustomerAddressExample();
        example.createCriteria().andUseridEqualTo(userId).andDefaultAddressEqualTo((byte) 1);
        List<CustomerAddress> address = customerAddressMapper.selectByExample(example);
        if (address != null && address.size() > 0) {
            return address.get(0);
        }
        return new CustomerAddress();
    }
}
