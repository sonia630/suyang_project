package com.o2o.massage.wechat;

import com.o2o.massage.core.utils.MD5Utils;

import java.util.*;

/**
 * @Author: zhongli
 * @Date: 2018/3/21 18:46
 * @Description:
 */
public class WeChatHelper {

    /**
     * 获取微信签名
     *
     * @param paraMap 请求参数集合
     * @return 微信请求签名串
     */
    public static String getSign(Map<String, String> paraMap, String signKey) {
        SortedMap<String, Object> map = new TreeMap<>(paraMap);
        StringBuffer sb = new StringBuffer();
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            //参数中sign、key不参与签名加密
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + signKey);
        String sign = MD5Utils.MD5(sb.toString()).toUpperCase();
        return sign;
    }

    /**
     * 解析微信服务器发来的请求
     *
     * @param inputStream
     * @return 微信返回的参数集合
     */
    /*public static SortedMap<String, Object> parseXml(InputStream inputStream) {
        SortedMap<String, Object> map = Maps.newTreeMap();
        try {
            //获取request输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            //得到xml根元素
            Element root = document.getRootElement();
            //得到根元素所有节点
            List<Element> elementList = root.elements();
            //遍历所有子节点
            for (Element element : elementList) {
                map.put(element.getName(), element.getText());
            }
            //释放资源
            inputStream.close();
        } catch (Exception e) {
            throw new ServiceException("微信工具类:解析xml异常", e);
        }
        return map;
    }*/

}
