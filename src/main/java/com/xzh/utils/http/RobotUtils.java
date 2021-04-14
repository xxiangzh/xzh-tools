package com.xzh.utils.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 机器人webhook
 *
 * @author: 向振华
 * @date 2020/07/03 15:43
 */
@Slf4j
//@Component
public class RobotUtils {

    private static String active;

    @Value("${spring.profiles.active}")
    public void setActive(String active) {
        RobotUtils.active = active;
    }

    /**
     * 机器人webhook
     */
    private static final String WEBHOOK = "https://oapi.dingtalk.com/robot/send?access_token=a940e07d21f13f07895112be5fa89046162a877019d2cc6ba1bb2aba04afcccc";

    /**
     * 发送消息
     */
    public static void send(String message) {
        message = "【" + active + "】" + LocalDateTime.now().withNano(0).toString().replace("T", " ") + " \n" + message;
        message = message.replace("{", "").replace("}", "").replace("\"", " ");
        String text = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"" + message + "\"}, \"at\": {\"isAtAll\": \"" + true + "\"}}";
        doSend(text);
    }

    private static void doSend(String text) {
        HttpPost httpPost = new HttpPost(WEBHOOK);
        httpPost.setEntity(new StringEntity(text, "UTF-8"));
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                response.close();
            }
            httpClient.close();
        } catch (Exception e) {
            log.error("机器人消息发送失败");
        }
    }
}