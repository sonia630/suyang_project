package com.o2o.nm.sys.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class BaseController {


    @Getter
    @Setter
    public static class TableResponse<T> {
        List<T> data;
    }

    protected Logger logger = LoggerFactory.getLogger(getClass());
    public static final String WEB_MESSAGE = "message";
    public static final String RENDER_JSON_SUCCESS = "success";
    public static final String RENDER_JSON_ERROR = "error";

    public String getRenderJson(String code, String message) {
        return "{\"code\":\"" + code + "\",\"message\":\"" + message + "\"}";
    }

    public String getSuccessJson(String message) {
        return "{\"code\":\"" + RENDER_JSON_SUCCESS + "\",\"message\":\"" + message + "\"}";
    }

    public String getErrorJson(String message) {
        return "{\"code\":\"" + RENDER_JSON_ERROR + "\",\"message\":\"" + message + "\"}";
    }

    public String getErrorRenderJson(String message, int statusCode, HttpServletResponse response) {
        response.setStatus(statusCode);
        return "{\"code\":\"" + RENDER_JSON_ERROR + "\",\"message\":\"" + message + "\"}";
    }

    protected Integer[] getParaValuesToInt(String name, HttpServletRequest request) {
        String[] values = request.getParameterValues(name);
        if (values == null)
            return null;
        Integer[] result = new Integer[values.length];
        for (int i = 0; i < result.length; i++)
            result[i] = Integer.parseInt(values[i]);
        return result;
    }

    protected Boolean getParaToBoolean(String name, Boolean defaultValue, HttpServletRequest request) {
        return toBoolean(request.getParameter(name), defaultValue);
    }

    private Boolean toBoolean(String value, Boolean defaultValue) {
        if (value == null || "".equals(value.trim()))
            return defaultValue;
        value = value.trim().toLowerCase();
        if ("1".equals(value) || "true".equals(value))
            return Boolean.TRUE;
        else if ("0".equals(value) || "false".equals(value))
            return Boolean.FALSE;
        throw new RuntimeException("Can not parse the parameter \"" + value + "\" to boolean value.");
    }

    public Integer getParaToInt(String name, Integer defaultValue, HttpServletRequest request) {
        return toInt(request.getParameter(name), defaultValue);
    }

    private Integer toInt(String value, Integer defaultValue) {
        if (value == null || "".equals(value.trim()))
            return defaultValue;
        if (value.startsWith("N") || value.startsWith("n"))
            return -Integer.parseInt(value.substring(1));
        return Integer.parseInt(value);
    }

    public String getRenderJson(Map<String, Object> pair) {
        return JSON.toJSONString(pair);
    }


    @RequestMapping(value = {"data"})
    @ResponseBody
    public ServerResponseData data(HttpServletRequest request) {
        int count = getPageCount(request);
        PagerSort pagerSort = new PagerSort(getCurrent(count, request), count, getParaToInt("start", 1, request),
                getSortName(request), isAsc(request));
        Page page = getPageData(pagerSort, request);
        return send(page, getParaToInt("draw", 1, request));
    }

    protected ServerResponseData send(Page page, int draw) {
        ServerResponseData<Object> bizServerData = new ServerResponseData<>();
        bizServerData.setRecordsTotal(page.getTotal());
        bizServerData.setRecordsFiltered(page.getTotal());
        bizServerData.setDraw(draw);
        bizServerData.setData(page.getRecords());
        return bizServerData;
    }


    protected int getCurrent(int count, HttpServletRequest request) {
        int start = getParaToInt("start", 0, request);
        int value = start / count;
        if (value == 0) {
            return value;
        } else {
            return value + 1;
        }
    }

    protected int getPageCount(HttpServletRequest request) {
        return getParaToInt("length", 90, request);
    }

    protected <T> Page<T> getPageData(PagerSort pagerSort, HttpServletRequest request) {
        throw new RuntimeException("child class must be overwrite it.");
    }

    protected boolean isAsc(HttpServletRequest request) {
        String sort0 = request.getParameter("order[0][dir]");
        boolean asc = true;
        if (sort0 != null && !sort0.equals("")) {
            if (sort0.equals("asc")) {
                asc = true;
            } else {
                asc = false;
            }
        }
        return asc;
    }

    protected String getSortName(String sort) {
        return null;
    }

    protected String getSortName(HttpServletRequest request) {
        String sort = request.getParameter("order[0][column]");
        if (StringUtils.isNotEmpty(sort)) {
            return getSortName(sort);
        }
        return null;
    }

    protected void renderText(HttpServletResponse response, String text) {
        render(response, text, "text/plain; charset=utf-8");
    }

    protected void renderJson(HttpServletResponse response, String text) {
        render(response, text, "application/json;charset=UTF-8");
    }

    protected void render(HttpServletResponse response, String text, String contentType) {
        try {
            response.setContentType(contentType);
            response.getWriter().write(text);
            response.getWriter().flush();
        } catch (IOException var3) {
            this.logger.error("{}", var3);
        }
    }

    @AllArgsConstructor
    @Getter
    protected class PagerSort {
        private int current;
        private int count;
        private int offset;
        private String sortName;
        private boolean asc;
    }


    @Getter
    @Setter
    protected class ServerResponseData<T> implements Serializable {
        private static final long serialVersionUID = 1L;
        private int draw;
        private int recordsTotal;
        private int recordsFiltered;
        private List<T> data;
    }
}
