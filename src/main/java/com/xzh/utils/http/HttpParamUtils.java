package com.xzh.utils.http;

import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 获取HTTP请求参数
 *
 * @author 向振华
 * @date 2022/07/08 11:49
 */
@Slf4j
public class HttpParamUtils {

    /**
     * 获取HTTP请求的form表单参数
     *
     * @param map               需要用什么类型的map封装参数
     * @param characterEncoding 编码格式
     * @return
     */
    public static Map<String, Object> getFormParam(Map<String, Object> map, String characterEncoding) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        if (servletRequestAttributes == null) {
            return map;
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();

        try {
            request.setCharacterEncoding(characterEncoding);
        } catch (UnsupportedEncodingException e) {
            log.error("编码失败 ", e);
            return map;
        }

        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap != null) {
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                map.put(entry.getKey(), entry.getValue()[0]);
            }
        }
        return map;
    }

    /**
     * 获取HTTP请求的json参数
     *
     * @param characterEncoding 编码格式
     * @return
     */
    public static String getJsonParam(String characterEncoding) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        if (servletRequestAttributes == null) {
            return "";
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();

        try {
            request.setCharacterEncoding(characterEncoding);
        } catch (UnsupportedEncodingException e) {
            log.error("编码失败 ", e);
            return "";
        }

        StringBuilder body = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            do {
                body.append(reader.readLine());
            } while (reader.read() != -1);
        } catch (Exception e) {
            log.error("请求体读取失败 ", e);
            return "";
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    log.error("关闭reader失败 ", e);
                }
            }
        }
        return body.toString();
    }

    /**
     * 获取HTTP请求体中的参数（可以是json、xml等）
     *
     * @param characterEncoding
     * @return
     */
    public static String getRequestParam(String characterEncoding) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        if (servletRequestAttributes == null) {
            return "";
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();

        try {
            request.setCharacterEncoding(characterEncoding);
        } catch (UnsupportedEncodingException e) {
            log.error("编码失败 ", e);
            return "";
        }

        try {
            ServletInputStream inputStream = request.getInputStream();
            return IoUtil.read(inputStream, characterEncoding);
        } catch (Exception e) {
            log.error("请求体读取失败 ", e);
            return "";
        }
    }
}
