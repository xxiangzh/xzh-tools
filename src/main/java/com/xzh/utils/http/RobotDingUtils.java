package com.xzh.utils.http;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;

/**
 * 钉钉机器人
 *
 * @author: 向振华
 * @date 2021/04/13 10:43
 */
@Slf4j
public class RobotDingUtils {

    private static final String WEBHOOK = "https://oapi.dingtalk.com/robot/send?access_token=18172910993fe31b54609801fbdae34e6d1316be8ff3b128088043efd262b209";
    private static final String SECRET = "SECf3325a13eaf568605b17abba7faad30ccb34ed4f028b96174cdbb5be98cba9cf";

    /**
     * 发送消息
     */
    public static void send(String message) {
        String text = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"" + message + "\"}, \"at\": {\"isAtAll\": \"" + true + "\"}}";
        Long timestamp = System.currentTimeMillis();
        String sign = sign(timestamp);
        String url = WEBHOOK + "&timestamp=" + timestamp + "&sign=" + sign;
        // HTTP请求
        httpRequest(url, text);
    }

    private static void httpRequest(String url, String text) {
        HttpRequest post = HttpUtil.createPost(url);
        post.body(text);
        HttpResponse execute = post.executeAsync();
        execute.close();
    }

    private static String sign(Long timestamp) {
        String stringToSign = timestamp + "\n" + SECRET;
        String sign = null;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(SECRET.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sign;
    }

    public static void main(String[] args) {
        send("你好啊!");
    }
}