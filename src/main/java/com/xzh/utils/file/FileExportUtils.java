package com.xzh.utils.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 文件导出工具
 *
 * @author 向振华
 * @date 2021/11/09 14:30
 */
@Slf4j
public class FileExportUtils {

    /**
     * 导出文件
     *
     * @param targetFolderDirectory 目标文件夹目录 D:\xxx
     * @param fileName              文件名 abc.txt
     * @param content               导出内容文本 如果在content加入\n，文件也会自动换行
     * @param charsetName           导出内容编码格式
     */
    public static void exportLocal(String targetFolderDirectory, String fileName, String content, String charsetName) {
        FileUtils.makeDirectory(targetFolderDirectory);
        try {
            OutputStream outputStream = new FileOutputStream(targetFolderDirectory + File.separator + fileName);
            IOUtils.write(content.getBytes(charsetName), outputStream);
        } catch (Exception e) {
            log.error("导出文件失败", e);
        }
    }

    /**
     * 导出文件
     *
     * @param fileName    文件名 abc.txt
     * @param content     导出内容文本 如果在content加入\n，文件也会自动换行
     * @param charsetName 导出内容编码格式
     */
    public static void exportResponse(String fileName, String content, String charsetName) {
        try {
            OutputStream outputStream = getServletOutputStream(fileName);
            IOUtils.write(content.getBytes(charsetName), outputStream);
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
