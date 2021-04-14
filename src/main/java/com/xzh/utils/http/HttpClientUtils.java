package com.xzh.utils.http;

import com.alibaba.fastjson.JSONObject;
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
 * http请求
 *
 * @author: 向振华
 * @date: 2020/06/08 16:44
 */
@Slf4j
public class HttpClientUtils {

    /**
     * get请求
     *
     * @param url 请求路径
     * @param map 请求参数
     * @return
     */
    public static String doGet(String url, Map<String, String> map) {
        HttpGet httpGet = new HttpGet(buildUri(url, map));
        return execute(httpGet);
    }

    /**
     * get请求
     *
     * @param url     请求路径
     * @param map     请求参数
     * @param headers 请求头
     * @return
     */
    public static String doGet(String url, Map<String, String> map, Map<String, String> headers) {
        HttpGet httpGet = new HttpGet(buildUri(url, map));
        addHeader(httpGet, headers);
        return execute(httpGet);
    }

    /**
     * post请求
     *
     * @param url  请求路径
     * @param json 请求参数json格式
     * @return
     */
    public static String doPost(String url, String json) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(json, "UTF-8"));
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        return execute(httpPost);
    }

    /**
     * post请求
     *
     * @param url     请求路径
     * @param json    请求参数json格式
     * @param headers 请求头
     * @return
     */
    public static String doPost(String url, String json, Map<String, String> headers) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(json, "UTF-8"));
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        addHeader(httpPost, headers);
        return execute(httpPost);
    }

    /**
     * 执行请求并返回string值
     *
     * @param httpUriRequest
     * @return
     */
    private static String execute(HttpUriRequest httpUriRequest) {
        try {
            return doExecute(httpUriRequest);
        } catch (Exception e) {
            log.error("HttpClientUtils-Exception: " + httpUriRequest.getURI(), e);
            throw new RuntimeException("http请求异常！");
        }
    }

    private static String doExecute(HttpUriRequest httpUriRequest) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpClient.execute(httpUriRequest);
            //请求体内容
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            log.info("HttpClientUtils-response: " + result);
            return result;
        } finally {
            if (response != null) {
                response.close();
            }
            httpClient.close();
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
            log.error("HttpClientUtils-Exception: " + url + JSONObject.toJSONString(map), e);
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
