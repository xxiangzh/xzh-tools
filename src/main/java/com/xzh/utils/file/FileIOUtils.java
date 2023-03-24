package com.xzh.utils.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件IO工具
 *
 * @author 向振华
 * @date 2021/11/09 14:30
 */
@Slf4j
public class FileIOUtils {

    /**
     * 读取文件
     *
     * @param pathname eg: D:\Z\123.txt
     * @return
     */
    public static List<String> readLines(String pathname) {
        List<String> list = new ArrayList<>();
        try {
            InputStream inputStream = new FileInputStream(pathname);
            list = IOUtils.readLines(inputStream, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("读取文件失败", e);
        }
        return list;
    }

    /**
     * 写文件到本地
     *
     * @param targetFolderDirectory 目标文件夹目录 D:\xxx
     * @param fileName              文件名 abc.txt
     * @param content               导出内容文本 如果在content加入\n，文件也会自动换行
     */
    public static void writeLocal(String targetFolderDirectory, String fileName, String content) {
        try {
            FileUtils.makeDirectory(targetFolderDirectory);
            OutputStream outputStream = new FileOutputStream(targetFolderDirectory + File.separator + fileName);
            byte[] data = content.getBytes(StandardCharsets.UTF_8);
            IOUtils.write(data, outputStream);
        } catch (Exception e) {
            log.error("导出文件失败", e);
        }
    }

    /**
     * 写文件到响应体
     *
     * @param fileName 文件名 abc.txt
     * @param content  导出内容文本 如果在content加入\n，文件也会自动换行
     */
    public static void writeResponse(String fileName, String content) {
        try {
            OutputStream outputStream = getServletOutputStream(fileName);
            byte[] data = content.getBytes(StandardCharsets.UTF_8);
            IOUtils.write(data, outputStream);
        } catch (Exception e) {
            log.error("导出文件失败", e);
        }
    }

    private static ServletOutputStream getServletOutputStream(String fileName) throws IOException {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        assert servletRequestAttributes != null;
        HttpServletResponse response = servletRequestAttributes.getResponse();
        assert response != null;
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        return response.getOutputStream();
    }
}
