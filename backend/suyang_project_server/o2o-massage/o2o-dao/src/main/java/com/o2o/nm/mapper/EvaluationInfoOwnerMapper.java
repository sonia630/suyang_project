package com.o2o.nm.mapper;

import com.o2o.nm.bo.EvalAllInfo;
import com.o2o.nm.bo.EvalBo;
import com.o2o.nm.bo.ProviderEval;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EvaluationInfoOwnerMapper {

    List<ProviderEval> getEvalList(@Param("providerId") String providerId, @Param("start") int start, @Param("size") int size);

    EvalAllInfo getEvalAllInfo(String providerId);

    List<ProviderEval> getEvalListByScoreRange(EvalBo evalBo);

    int getEvalListByScoreRangeCount(EvalBo evalBo);
}
