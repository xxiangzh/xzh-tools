package com.xzh.utils.file;

import com.xzh.utils.http.HttpServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

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
        HttpServletResponse response = HttpServletUtils.response();
        assert response != null;
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
