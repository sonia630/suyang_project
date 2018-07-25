package com.o2o.massage.web.common.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: lawrence
 * Date: 2017/9/6 19:39
 * Description:
 */
public class RequestCaptureFilter extends OncePerRequestFilter {
    public static final String KEY_REQUEST_BODY_OBJECT="custom_request_body";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            CustomHttpServletRequestWrapper httpServletRequestWrapper = new CustomHttpServletRequestWrapper(request);
            String requestBody = CustomHttpServletRequestWrapper.getBodyString(httpServletRequestWrapper);
            request.setAttribute(KEY_REQUEST_BODY_OBJECT,requestBody);
            filterChain.doFilter(httpServletRequestWrapper, response);
        }else{
            filterChain.doFilter(request, response);
        }
    }
}
