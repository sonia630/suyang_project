package com.o2o.massage;

import com.o2o.massage.dao.entity.UserBaseInfo;
import com.o2o.massage.model.UserBaseInfoVO;
import org.springframework.beans.BeanUtils;

/**
 * @Author: zhongli
 * @Date: 2018/2/23 20:28
 * @Description:
 */
public class ModelTransHelper {

    public static UserBaseInfoVO transfer(UserBaseInfo userBaseInfo) {
        UserBaseInfoVO userInfoVO = new UserBaseInfoVO();
        BeanUtils.copyProperties(userBaseInfo,userInfoVO);
        return userInfoVO;
    }
}
