package com.o2o.massage.core.utils;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lawrence on 12/06/2017.
 */
public class HttpClientHelper {

    private final static Logger logger = LoggerFactory
            .getLogger(HttpClientHelper.class);

    public static String doPost(String targetUrl, Map<String, String> params) throws Exception {
        return doPost(targetUrl, params, 3 * 1000 * 10);
    }

    public static String doPost(String targetUrl, Map<String, String> params, int timeout) throws Exception {

        // logger.info("Do post with targetUrl:{},params:{}",targetUrl,params);
        String result = null;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(targetUrl.trim());
        httpPost.setHeader("Content-Type", "text/html;charset=UTF-8");
        if (params != null) {
            List<NameValuePair> mvps = buildRequestParams(params);

            httpPost.setEntity(new UrlEncodedFormEntity(mvps, Consts.UTF_8));
            //httpPost.setEntity(new StringEntity(JSONUtils.writeValue(params)));
        }

        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
                .build();
        httpPost.setConfig(requestConfig);

        CloseableHttpResponse response = null;
        try {

            response = httpclient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity == null) {
                    String error = String.format("No response returned for targetUrl:%s", targetUrl);
                    logger.info("Do post with targetUrl:{},params:{},error:{}", targetUrl, params, error);
                    throw new Exception(error);
                }

                result = EntityUtils.toString(resEntity, Consts.UTF_8);
                EntityUtils.consume(resEntity);

            } else {
                String error = String.format("Unexpected response status:%s for targetUrl: %s", status, targetUrl);
                logger.info("Do post with targetUrl:{},params:{},error:{}", targetUrl, params, error);
                throw new Exception(error);
            }
        } catch (IOException e) {
            throw new Exception(e);
        } finally {
            if (httpPost != null) {
                httpPost.abort();
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    String error = String.format("Close httpclient throws exception,targetUrl:%s", targetUrl);
                    logger.info("Do post with targetUrl:{},params:{},error:{}", targetUrl, params, error);
                    logger.error(error, e);
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    String error = String.format("Close HttpRespons throws exception,targetUrl:%s", targetUrl);
                    logger.info("Do post with targetUrl:{},params:{},error:{}", targetUrl, params, error);
                    logger.error(error, e);
                }
            }
        }
        logger.info("Do post with targetUrl:{},params:{},result:{}", targetUrl, params, result);
        return result;
    }

    public static String doPost(String targetUrl, String body) throws Exception {

       /* , SSLConnectionSocketFactory sslsf*/
        String result = null;

        CloseableHttpClient httpclient = HttpClients.custom().build();

        HttpPost httpPost = new HttpPost(targetUrl.trim());
        //
        if (org.apache.commons.lang.StringUtils.isNotBlank(body)) {
            StringEntity stringEntity = new StringEntity(body, "UTF-8");
            httpPost.setEntity(stringEntity);
        }

        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
        httpPost.setConfig(requestConfig);

        CloseableHttpResponse response = null;
        try {

            response = httpclient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity == null) {
                    String error = String.format("No response returned for targetUrl:%s", targetUrl);
                    logger.info("Do post with targetUrl:{},body:{},error:{}", targetUrl, body, error);
                    throw new Exception(error);
                }

                result = EntityUtils.toString(resEntity, Consts.UTF_8);
                EntityUtils.consume(resEntity);

            } else {
                String error = String.format("Unexpected response status:%s for targetUrl: %s", status, targetUrl);
                logger.info("Do post with targetUrl:{},body:{},error:{}", targetUrl, body, error);
                throw new Exception(error);
            }
        } catch (IOException e) {
            throw new Exception(e);
        } finally {
            if (httpPost != null) {
                httpPost.abort();
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    String error = String.format("Close httpclient throws exception,targetUrl:%s", targetUrl);
                    logger.info("Do post with targetUrl:{},body:{},error:{}", targetUrl, body, error);
                    logger.error(error, e);
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    String error = String.format("Close HttpRespons throws exception,targetUrl:%s", targetUrl);
                    logger.info("Do post with targetUrl:{},body:{},error:{}", targetUrl, body, error);
                    logger.error(error, e);
                }
            }
        }
        logger.info("Do post with targetUrl:{},body:{},result:{}", targetUrl, body, result);
        return result;
    }

    @SuppressWarnings("resource")
    public static String doGet(String targetUrl, Map<String, String> paraMap, Map<String, String> headers) throws Exception {

        String result = null;

        HttpClient httpClient = null;

        List<NameValuePair> query = buildRequestParams(paraMap);

        // 要传递的参数.

        String queryString = URLEncodedUtils.format(query, "UTF-8");
        if (targetUrl.indexOf("?") > 0) {
            targetUrl = targetUrl + "&" + queryString;
        } else {
            targetUrl = targetUrl + "?" + queryString;
        }

        HttpGet httpGet = new HttpGet(targetUrl);
        if (headers != null) {
            Set<String> headerKeySet = headers.keySet();
            for (String headerKey : headerKeySet) {
                httpGet.setHeader(headerKey, headers.get(headerKey));
            }
        }

        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(500).setConnectTimeout(500).build();
        httpGet.setConfig(requestConfig);
        HttpResponse response = null;
        try {
            httpClient = new DefaultHttpClient();
            response = httpClient.execute(httpGet);

            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity == null) {
                    String error = String.format("No response returned for targetUrl:%s", targetUrl);
                    logger.info("Do get with targetUrl:{},params:{},error:{}", targetUrl, paraMap, error);
                    throw new Exception(error);
                }
                result = EntityUtils.toString(resEntity, Consts.UTF_8);

            } else {
                String error = String.format("Unexpected response status:%s for targetUrl: %s", status, targetUrl);
                logger.info("Do get with targetUrl:{},params:{},error:{}", targetUrl, paraMap, error);
                throw new Exception(error);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            if (httpGet != null) {
                httpGet.abort();
                httpGet.releaseConnection();
            }

        }
        //logger.info("Do get with targetUrl:{},params:{},result:{}", targetUrl, paraMap, result);

        return result;

    }

    protected static List<NameValuePair> buildRequestParams(Map<String, String> params) {
        List<NameValuePair> mvps = new ArrayList<NameValuePair>();
        if (params != null) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                mvps.add(new BasicNameValuePair(key, params.get(key)));
            }
        }

        return mvps;
    }

    public static String doPost(String targetUrl, Map<String, String> urlParams, String body) throws Exception {
        return doPost(targetUrl, urlParams, body, 3000);
    }

    /**
     * Post中，一部分参数在url中，另外一部分在body中。
     *
     * @param targetUrl
     * @param urlParams
     * @param body
     * @return
     * @throws Exception
     */
    public static String doPost(String targetUrl, Map<String, String> urlParams, String body, int timeout) throws Exception {

        URIBuilder builder = new URIBuilder(targetUrl);
        builder.addParameters(buildRequestParams(urlParams));
        HttpPost httpPost = new HttpPost(builder.build());
        httpPost.setHeader("Content-Type", "text/html;charset=UTF-8");

        logger.info("Do post with targetUrl:{},params:{}", httpPost.getURI().toString(), urlParams);
        StringEntity entity = new StringEntity(body, Consts.UTF_8);
        return postAction(httpPost, entity, timeout);
    }

    private static String postAction(HttpPost httpPost, HttpEntity entity, int timeout) throws Exception {
        String result = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        if (entity != null) {
            httpPost.setEntity(entity);
        }
        String targetUrl = httpPost.getURI().toString();
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(timeout).setConnectTimeout(timeout).build();
        httpPost.setConfig(requestConfig);

        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity == null) {
                    String error = String.format("No response returned for targetUrl:%s", targetUrl);
                    logger.info("Do post with targetUrl:{},params:{},error:{}", targetUrl, entity, error);
                    throw new Exception(error);
                }

                result = EntityUtils.toString(resEntity, Consts.UTF_8);
                EntityUtils.consume(resEntity);

            } else {
                String error = String.format("Unexpected response status:%s for targetUrl: %s",
                        status, targetUrl);
                logger.info("Do post with targetUrl:{},entity:{},error:{}", targetUrl, entity, error);
                throw new Exception(error);
            }
        } catch (Exception ex) {
            logger.error("Do post with targetUrl:{},error:{}", targetUrl, ex.getMessage());
            throw ex;
        } finally {
            if (httpPost != null) {
                httpPost.abort();
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    String error = String.format("Close httpclient throws exception,targetUrl:%s", targetUrl);
                    logger.info("Do post with targetUrl:{},entity:{},error:{}", targetUrl, entity, error);
                    logger.error(error, e);
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    String error = String.format("Close HttpRespons throws exception,targetUrl:%s", targetUrl);
                    logger.info("Do post with targetUrl:{},entity:{},error:{}", targetUrl, entity, error);
                    logger.error(error, e);
                }
            }
        }
        logger.info("Do post with targetUrl:{},entity:{},result:{}", targetUrl, entity, result);
        return result;
    }

    public static String sendPost(String postDataStr, String url) throws Exception {
        String result = null;
        CloseableHttpClient httpclient = null;
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            httpPost = new HttpPost(url);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("data", postDataStr));
            httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000)
                    .setConnectTimeout(3000).build();// 设置请求和传输超时时间
            httpPost.setConfig(requestConfig);

            response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String responseString = EntityUtils.toString(resEntity, Consts.UTF_8);
                    result = responseString;
                    //result = JSONUtils.parse(responseString, JsonNode.class);
                    EntityUtils.consume(resEntity);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (httpPost != null) {
                httpPost.abort();
            }
            if (httpclient != null) {
                httpclient.close();
            }
            if (response != null) {
                response.close();
            }
        }
        return result;
    }

}
