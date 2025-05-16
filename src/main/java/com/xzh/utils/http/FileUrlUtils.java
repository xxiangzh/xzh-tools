package com.xzh.utils.http;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 向振华
 * @date 2025/05/13 09:39
 */
public class FileUrlUtils {

    /**
     * 将文件URL写入 response OutputStream
     *
     * @param fileUrl
     */
    public static void writeResponse(String fileUrl) {
        HttpRequest httpRequest = HttpUtil.createGet(fileUrl);
        HttpServletResponse curResponse = HttpServletUtils.response();

        try (HttpResponse httpResponse = httpRequest.execute()) {
            // 检查远程响应状态
            if (!httpResponse.isOk()) {
                curResponse.sendError(httpResponse.getStatus(), "远程文件获取失败");
                return;
            }

            // 设置响应头
            String contentType = httpResponse.header("Content-Type");
            curResponse.setContentType(contentType != null ? contentType : MediaType.APPLICATION_OCTET_STREAM_VALUE);
            String contentDisposition = httpResponse.header("Content-Disposition");
            curResponse.setHeader("Content-Disposition", contentDisposition != null ? contentDisposition : "attachment; filename=" + System.currentTimeMillis());

            try (OutputStream out = curResponse.getOutputStream()) {
                // Hutool直接将远程响应体写入本地响应流
                httpResponse.writeBody(out, false, null);
            }
        } catch (IOException e) {
            try {
                curResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "文件传输失败");
            } catch (IOException ex) {
                throw new RuntimeException("文件传输异常", ex);
            }
        }
    }
}
