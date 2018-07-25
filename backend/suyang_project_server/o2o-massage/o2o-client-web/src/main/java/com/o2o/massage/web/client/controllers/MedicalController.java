package com.o2o.massage.web.client.controllers;

import com.o2o.massage.core.common.EnumApiRequestSide;
import com.o2o.massage.core.exception.BizException;
import com.o2o.massage.web.client.aop.ClientUserIdentify;
import com.o2o.massage.web.client.aop.EnumRequiredUserType;
import com.o2o.massage.web.common.context.ApiRequestMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/medical")
@Api(description = "病案相关接口")
public class MedicalController {


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ApiOperation(value = "病案首页")
    @ApiRequestMethod(requestSide = EnumApiRequestSide.CLIENT)
    @ClientUserIdentify(mustLogin = true, requiredClientUserType = EnumRequiredUserType.SERVICE_PROVIDER)
    public String index() throws BizException {
        return "provider/medical_record";
    }

}
