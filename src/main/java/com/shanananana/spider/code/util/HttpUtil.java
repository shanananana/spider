package com.shanananana.spider.code.util;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.util.Map;

/**
 * @author shana
 * @description Http工具包
 * @date 2022/11/22 21:23
 */
public class HttpUtil {


    @SneakyThrows
    public static String doGetWithOutHeader(String url) {
        try {
            System.out.printf("开始请求 url %s %n", url);
            CloseableHttpResponse response = HttpClients.createDefault().execute(new HttpGet(url));
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            System.out.printf("do httpGet catch exception: url %s %n", url);
            e.printStackTrace();
        }
        return null;
    }

    @SneakyThrows
    public static String doGetWithHeader(String url, Map<String, String> headerMap) {
        try {
            System.out.printf("开始请求 url %s %n headerMap %s", url, JSON.toJSON(headerMap));
            HttpGet request = new HttpGet(url);
            headerMap.forEach(request::addHeader);
            CloseableHttpResponse response = HttpClients.createDefault().execute(request);
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            System.out.printf("do httpGet catch exception: url %s %n", url);
            e.printStackTrace();
        }
        return null;
    }

}
