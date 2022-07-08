package com.xzh.utils.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * txt文件导出
 *
 * @author 向振华
 * @date 2021/11/09 14:30
 */
@Slf4j
public class TxtUtils {

    /**
     * 导出txt文件
     * 如果在content加入\n，文件也会自动换行
     *
     * @param fileName
     * @param content
     */
    public static void export(String fileName, String content, String charsetName) {
        ServletOutputStream servletOutputStream = getServletOutputStream(fileName);
        try {
            IOUtils.write(content.getBytes(charsetName), servletOutputStream);
        } catch (Exception e) {
            log.error("导出文件失败", e);
        }
    }

    /**
     * 导出txt文件
     * 会自动加入换行
     *
     * @param fileName
     * @param contents
     */
    public static void export(String fileName, List<String> contents, String charsetName) {
        ServletOutputStream servletOutputStream = getServletOutputStream(fileName);
        try {
            IOUtils.writeLines(contents, null, servletOutputStream, charsetName);
        } catch (Exception e) {
            log.error("导出文件失败", e);
        }
    }

    private static ServletOutputStream getServletOutputStream(String fileName) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        if (servletRequestAttributes == null) {
            return null;
        }
        HttpServletResponse response = servletRequestAttributes.getResponse();
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment;filename=" + getFileName(fileName));
        try {
            return response.getOutputStream();
        } catch (Exception e) {
            return null;
        }
    }

    private static String getFileName(String fileName) {
        return fileName.endsWith(".txt") ? fileName : fileName + ".txt";
    }
}
