package com.xzh.utils.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HTTP请求
 *
 * @author: 向振华
 * @date: 2020/06/08 16:44
 */
@Slf4j
public class HttpClientUtils {

    /**
     * get请求
     *
     * @param url     请求路径
     * @param map     请求参数
     * @param headers 请求头
     * @return
     */
    public static String get(String url, Map<String, String> map, Map<String, String> headers) {
        HttpGet httpGet = new HttpGet(buildUri(url, map));
        addHeader(httpGet, headers);
        log.info("HTTP请求 url " + url);
        log.info("HTTP请求 request " + map);
        return execute(httpGet);
    }

    /**
     * post请求
     *
     * @param url     请求路径
     * @param json    请求参数json格式
     * @param headers 请求头
     * @return
     */
    public static String post(String url, String json, Map<String, String> headers) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(json, "UTF-8"));
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        addHeader(httpPost, headers);
        log.info("HTTP请求 url " + url);
        log.info("HTTP请求 request " + json);
        return execute(httpPost);
    }

    private static String execute(HttpUriRequest httpUriRequest) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpUriRequest);
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            log.info("HTTP请求 response " + result);
            return result;
        } catch (Exception e) {
            log.error("HTTP请求 exception ", e);
            throw new RuntimeException("HTTP请求异常！");
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * 创建URI
     *
     * @param url
     * @param map
     * @return
     */
    private static URI buildUri(String url, Map<String, String> map) {
        try {
            if (map != null && map.size() > 0) {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                for (String key : map.keySet()) {
                    nameValuePairs.add(new BasicNameValuePair(key, map.get(key)));
                }
                return new URIBuilder(url).addParameters(nameValuePairs).build();
            } else {
                return new URIBuilder(url).build();
            }
        } catch (Exception e) {
            log.error("URI创建异常 exception: " + url + map, e);
            throw new RuntimeException("URI创建异常！");
        }
    }

    /**
     * 添加请求头
     *
     * @param httpUriRequest
     * @param headers
     */
    private static void addHeader(HttpUriRequest httpUriRequest, Map<String, String> headers) {
        if (httpUriRequest != null && headers != null && headers.size() > 0) {
            for (String key : headers.keySet()) {
                httpUriRequest.setHeader(key, headers.get(key));
            }
        }
    }
}
