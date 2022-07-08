package com.xzh.utils.http;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 获取当前请求
 *
 * @author 向振华
 * @date 2019年3月22日
 */
public class HttpServletUtils {

    /**
     * 获取当前响应
     *
     * @return
     */
    public static HttpServletResponse response() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        if (servletRequestAttributes == null) {
            throw new RuntimeException("ServletRequestAttributes is null");
        }
        HttpServletResponse response = servletRequestAttributes.getResponse();
        if (response == null) {
            throw new RuntimeException("HttpServletResponse is null");
        }
        return response;
    }

    /**
     * 获取当前请求
     *
     * @return
     */
    public static HttpServletRequest request() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        if (servletRequestAttributes == null) {
            throw new RuntimeException("ServletRequestAttributes is null");
        }
        return servletRequestAttributes.getRequest();
    }

    /**
     * 获取当前session
     *
     * @return
     */
    public static HttpSession session() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        if (servletRequestAttributes == null) {
            throw new RuntimeException("ServletRequestAttributes is null");
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request.getSession();
    }
}
