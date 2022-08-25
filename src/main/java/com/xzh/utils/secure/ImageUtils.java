package com.xzh.utils.secure;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

/**
 * 图片BASE64编码解码工具
 *
 * @author 向振华
 * @date 2019/09/18 10:35
 */
public class ImageUtils {

    /**
     * 本地图片转BASE64编码
     *
     * @param imgFile
     * @return
     */
    public static String local2Base64(String imgFile) {
        InputStream is = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            is = new FileInputStream(imgFile);
            data = new byte[is.available()];
            is.read(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        // 转BASE64编码
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * 网络图片转BASE64编码
     *
     * @param imgUrl
     * @return
     */
    public static String online2Base64(String imgUrl) {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        InputStream is = null;
        try {
            // 创建URL
            URL url = new URL(imgUrl);
            byte[] by = new byte[1024];
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            is = conn.getInputStream();
            // 将内容读取内存中
            int len = -1;
            while ((len = is.read(by)) != -1) {
                data.write(by, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        // BASE64编码
        return Base64.getEncoder().encodeToString(data.toByteArray());
    }

    /**
     * BASE64编码转图片
     *
     * @param base64ImgStr
     * @param imgFilePath  图片地址
     * @return
     */
    public static boolean base642Image(String base64ImgStr, String imgFilePath) {
        if (base64ImgStr == null || base64ImgStr.isEmpty()) {
            return false;
        }
        // BASE64解码
        byte[] bytes = Base64.getDecoder().decode(base64ImgStr);
        OutputStream os = null;
        try {
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {
                    // 调整异常数据
                    bytes[i] += 256;
                }
            }
            os = new FileOutputStream(imgFilePath);
            os.write(bytes);
            os.flush();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}