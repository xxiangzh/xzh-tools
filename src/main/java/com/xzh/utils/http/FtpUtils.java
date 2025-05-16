package com.xzh.utils.http;

import cn.hutool.extra.ftp.Ftp;
import cn.hutool.extra.ftp.FtpConfig;
import cn.hutool.extra.ftp.FtpMode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 向振华
 * @date 2025/05/12 10:21
 */
@Slf4j
public class FtpUtils {

    /**
     * 获取文件列表
     *
     * @param config
     * @return
     */
    public static List<String> fileList(FtpConfig config) {
        // FTP配置
        String host = config.getHost();
        int port = config.getPort();
        String user = config.getUser();
        String password = config.getPassword();
        String path = "/";

        List<String> list = new ArrayList<>();
        try (Ftp ftp = new Ftp(host, port, user, password)) {
            // 切换被动模式（Ftp中默认是主动模式）
            ftp.setMode(FtpMode.Passive);
            FTPClient client = ftp.getClient();
            FTPFile[] files = client.listFiles(path);
            for (FTPFile file : files) {
                list.add(file.getName());
            }
        } catch (Exception e) {
            log.error("FTP服务器 请求异常 ", e);
        }
        return list;
    }

    /**
     * 将FTP文件写入 response OutputStream
     *
     * @param config
     * @param fileName
     */
    public static void writeResponse(FtpConfig config, String fileName) {
        // FTP配置
        String host = config.getHost();
        int port = config.getPort();
        String user = config.getUser();
        String password = config.getPassword();
        String path = "/";

        // 请求头
        HttpServletResponse response = HttpServletUtils.response();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        try (Ftp ftp = new Ftp(host, port, user, password)) {
            // 切换被动模式（Ftp中默认是主动模式）
            ftp.setMode(FtpMode.Passive);
            boolean exist = ftp.exist(path + fileName);
            if (exist) {
                log.info("FTP服务器 下载文件 " + fileName);
                // 将文件写入 response OutputStream
                try (OutputStream out = response.getOutputStream()) {
                    ftp.download(path, fileName, out);
                } catch (IOException e) {
                    log.error("文件写入异常 ", e);
                    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                }
            } else {
                log.error("FTP服务器 不存在文件 " + fileName);
                response.setStatus(HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception e) {
            log.error("FTP连接异常 ", e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
