package com.o2o.massage.web.common;

import com.o2o.massage.core.common.WebRespResult;
import com.o2o.massage.core.component.SpringContextHolder;
import com.o2o.massage.core.exception.BizException;
import com.o2o.massage.core.exception.NeedLoginException;
import com.o2o.massage.core.exception.TokenIllegalException;
import com.o2o.massage.dao.entity.type.EnumUserType;
import com.o2o.massage.web.Constants;
import com.o2o.massage.web.common.context.ClientRequestContext;
import com.o2o.massage.web.common.helper.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import static com.o2o.massage.core.common.WebRespResult.ERROR;


/**
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    //在LogInterceptor中使用
    public static final String CURRENT_REQUEST_EXCEPTION = "nexus_request_exception";

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final int NORMAL_HTTP_RESP_CODE = 200;

    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public WebRespResult baseApiExceptionHandler(HttpServletRequest req, HttpServletResponse resp, BizException e) {
        String fromUid = UserUtils.getFromUid(req);
        String requestUri = req.getRequestURI();
        req.setAttribute(CURRENT_REQUEST_EXCEPTION, e);
        if (e.getHttpRespCode() != NORMAL_HTTP_RESP_CODE) {
            resp.setStatus(e.getHttpRespCode());
        }
        logger.error(String.format("error while requesting uri:%s,fromuid:%s,httpRespCode:%s",
                requestUri, fromUid, e.getHttpRespCode()), e);
        return e.getErrorResult(SpringContextHolder.getApplicationContext(), Locale.CHINESE);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public WebRespResult exceptionHandler(HttpServletRequest req, Exception e) {
        String fromUid = UserUtils.getFromUid(req);
        String requestUri = req.getRequestURI();
        req.setAttribute(CURRENT_REQUEST_EXCEPTION, e);
        logger.error(String.format("error while requesting uri:%s,fromuid:%s", requestUri, fromUid), e);
        return new WebRespResult(ERROR, e.getMessage());
    }

    @ExceptionHandler(value = {NeedLoginException.class, TokenIllegalException.class})
    public String needLoginExceptionHandler(HttpServletRequest req, Exception e) {
        String fromUid = UserUtils.getFromUid(req);
        String requestUri = req.getRequestURI();
        logger.error(String.format("error while requesting uri:%s,fromuid:%s", requestUri, fromUid), e);
        ClientRequestContext clientRequestContext = ClientRequestContext.getCurrent();
        if (clientRequestContext != null) {
            if (EnumUserType.isProvider(clientRequestContext.getUserType())) {
                return "redirect:/" + Constants.provider_login_url + "?from=" + clientRequestContext.getRequestUrl() + "?" + getQueryString(req);
            }
        }
        return "redirect:/" + Constants.client_login_url + "?from=" + clientRequestContext.getRequestUrl() + "?" + getQueryString(req);
    }

    private String getQueryString(HttpServletRequest req) {
        if (StringUtils.isNotEmpty(req.getQueryString())) {
            if (req.getQueryString().contains("&")) {
                return req.getQueryString().replaceAll("&", "\\$\\$\\$");
            }
        }
        return "";
    }

}
