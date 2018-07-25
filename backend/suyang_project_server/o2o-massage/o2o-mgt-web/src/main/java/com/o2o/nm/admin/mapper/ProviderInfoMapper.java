package com.o2o.nm.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.o2o.nm.admin.entity.ProviderInfo;
import com.o2o.nm.admin.vo.ProviderDetail;

/**
 * <p>
 * 服务提供者基本信息表 Mapper 接口
 * </p>
 *
 * @author warning5
 * @since 2018-03-11
 */
public interface ProviderInfoMapper extends BaseMapper<ProviderInfo> {

    ProviderDetail getProviderDetailInfo(String userId);
}
