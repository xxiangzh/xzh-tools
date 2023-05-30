package com.xzh.utils.http;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * java原生http调用
 *
 * @author 向振华
 * @date 2022/04/11 15:05
 */
public class HttpURLConnectionUtils {

    /**
     * Http get请求
     *
     * @param httpUrl 连接
     * @param headers 请求头
     * @return 响应数据
     */
    public static String get(String httpUrl, Map<String, String> headers) {
        //链接
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        try {
            //创建连接
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            //设置请求方式
            connection.setRequestMethod("GET");
            //设置连接超时时间
            connection.setReadTimeout(30000);
            //设置请求头
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            //开始连接
            connection.connect();
            //获取响应数据
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                //获取返回的数据
                is = connection.getInputStream();
                if (null != is) {
                    br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                    String temp = null;
                    while (null != (temp = br.readLine())) {
                        result.append(temp);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //关闭远程连接
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result.toString();
    }

    /**
     * Http post请求
     *
     * @param httpUrl 连接
     * @param param   参数
     * @param headers 请求头
     * @return 响应数据
     */
    public static String post(String httpUrl, String param, Map<String, String> headers) {
        StringBuilder result = new StringBuilder();
        //连接
        HttpURLConnection connection = null;
        OutputStream os = null;
        InputStream is = null;
        BufferedReader br = null;
        try {
            //创建连接对象
            URL url = new URL(httpUrl);
            //创建连接
            connection = (HttpURLConnection) url.openConnection();
            //设置请求方法
            connection.setRequestMethod("POST");
            //设置连接超时时间
            connection.setConnectTimeout(30000);
            //设置读取超时时间
            connection.setReadTimeout(30000);
            //DoOutput设置是否向httpUrlConnection输出，DoInput设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            //设置是否可读取
            connection.setDoOutput(true);
            connection.setDoInput(true);
            //设置请求头
            connection.setRequestProperty("content-type", "application/json;charset=utf-8");
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            //拼装参数
            if (null != param) {
                //设置参数
                os = connection.getOutputStream();
                //拼装参数
                os.write(param.getBytes(StandardCharsets.UTF_8));
            }
            //开启连接
            connection.connect();
            //读取响应
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                is = connection.getInputStream();
                if (null != is) {
                    br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                    String temp;
                    while (null != (temp = br.readLine())) {
                        result.append(temp);
                        result.append("\r\n");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭连接
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //关闭连接
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result.toString();
    }
}