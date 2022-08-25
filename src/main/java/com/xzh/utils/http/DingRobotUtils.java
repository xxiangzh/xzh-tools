package com.xzh.utils.http;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 钉钉机器人
 *
 * @author: 向振华
 * @date 2021/04/13 10:43
 */
@Slf4j
public class DingRobotUtils {

    private static final String WEBHOOK = "https://oapi.dingtalk.com/robot/send?access_token=18172910993fe31b54609801fbdae34e6d1316be8ff3b128088043efd262b209";
    private static final String SECRET = "";

    /**
     * 发送消息
     */
    public static void send(String content) {
        String url = getUrl();
        String text = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"" + content + "\"}, \"at\": {\"isAtAll\": \"" + false + "\"}}";
        // 发送HTTP请求
        httpRequest(url, text);
    }

    private static String getUrl() {
        if (StringUtils.isBlank(SECRET)) {
            return WEBHOOK;
        } else {
            Long timestamp = System.currentTimeMillis();
            String ts = timestamp + "\n" + SECRET;
            String sign = null;
            try {
                Mac mac = Mac.getInstance("HmacSHA256");
                mac.init(new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
                byte[] signData = mac.doFinal(ts.getBytes(StandardCharsets.UTF_8));
                sign = URLEncoder.encode(Base64.getEncoder().encodeToString(signData), "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return WEBHOOK + "&timestamp=" + timestamp + "&sign=" + sign;
        }
    }

    private static void httpRequest(String url, String text) {
        HttpUtil.createPost(url)
                .body(text)
                .executeAsync();
    }

    public static void main(String[] args) {
        send("你好啊!");
    }
}