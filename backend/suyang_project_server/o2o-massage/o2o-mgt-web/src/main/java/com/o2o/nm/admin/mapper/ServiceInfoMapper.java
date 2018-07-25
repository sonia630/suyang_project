package com.o2o.nm.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.o2o.nm.admin.entity.ServiceInfo;
import com.o2o.nm.admin.vo.ServiceDefVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 服务定义表 Mapper 接口
 * </p>
 *
 * @author warning5
 * @since 2018-03-05
 */
public interface ServiceInfoMapper extends BaseMapper<ServiceInfo> {

    List<ServiceDefVo> selectServiceDefs(String userId);

    ServiceDefVo selectServiceDef(@Param("userId") String defaultProviderId,
                                  @Param("serviceId") String serviceId,
                                  @Param("defaultProvider") boolean defaultProvider);

    void changeServiceStatus(@Param("serviceIds") List<String> serviceIds, @Param("status") int enable);

    List<ServiceDefVo> getUserRelationServices(@Param("defaultProviderId") String defaultProviderId, @Param("providerId") String userId);

    ServiceDefVo getRelationService(String relationId);
}
