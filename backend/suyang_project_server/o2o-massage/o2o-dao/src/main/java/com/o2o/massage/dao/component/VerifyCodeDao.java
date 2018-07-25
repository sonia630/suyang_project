package com.o2o.massage.dao.component;

import com.o2o.massage.core.constants.VerifyCodeScenario;
import com.o2o.massage.core.constants.VerifyCodeState;
import com.o2o.massage.dao.entity.VerifyCodeInfo;
import com.o2o.massage.dao.entity.VerifyCodeInfoExample;
import com.o2o.massage.dao.mapper.VerifyCodeInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Author: zhongli
 * @Date: 2018/2/23 15:12
 * @Description:
 */
@Component
public class VerifyCodeDao {

    @Autowired
    private VerifyCodeInfoMapper verifyCodeInfoMapper;

    public int addOneCodeInfo(String phone, int code,VerifyCodeScenario scenario) {
        VerifyCodeInfo codeInfo = new VerifyCodeInfo();
        codeInfo.setPhoneNo(phone);
        codeInfo.setVerifyCode((short) code);
        codeInfo.setCreateTime(new Date());
        codeInfo.setScenario((byte)scenario.getValue());
        return verifyCodeInfoMapper.insertSelective(codeInfo);
    }

    public int updateStateById(long id) {
        VerifyCodeInfo codeInfo = new VerifyCodeInfo();
        codeInfo.setId(id);
        codeInfo.setCodeState(VerifyCodeState.USED.getValue());
        return verifyCodeInfoMapper.updateByPrimaryKeySelective(codeInfo);
    }

    public VerifyCodeInfo getCodeByPhone(String phone,VerifyCodeScenario scenario) {
        VerifyCodeInfoExample example = new VerifyCodeInfoExample();
        VerifyCodeInfoExample.Criteria criteria = example.createCriteria();
        criteria
                .andCodeStateEqualTo(VerifyCodeState.UNUSED.getValue())
                .andPhoneNoEqualTo(phone)
        .andScenarioEqualTo((byte)scenario.getValue());
        example.setOrderByClause("create_time desc");
        List<VerifyCodeInfo> list = verifyCodeInfoMapper.selectByExample(example);
        return list == null || list.size() == 0 ? null : list.get(0);
    }
}
