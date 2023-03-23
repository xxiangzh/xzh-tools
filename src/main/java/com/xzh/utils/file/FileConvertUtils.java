package com.xzh.utils.file;

import org.apache.http.entity.ContentType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * 文件转换工具
 *
 * @author 向振华
 * @date 2020/08/31 14:44
 */
public class FileConvertUtils {

    /**
     * 文件转换
     *
     * @param multipartFile
     * @return
     */
    public static File getFile(MultipartFile multipartFile) {
        File file = null;
        try {
            file = File.createTempFile("tmp", null);
            multipartFile.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 文件转换
     *
     * @param file
     * @return
     */
    public static MultipartFile getMultipartFile(File file) {
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        final byte[] content;
        try {
            content = FileCopyUtils.copyToByteArray(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return new MultipartFile() {
            @Override
            public String getName() {
                return file.getName();
            }

            @Override
            public String getOriginalFilename() {
                return file.getName();
            }

            @Override
            public String getContentType() {
                return ContentType.APPLICATION_OCTET_STREAM.toString();
            }

            @Override
            public boolean isEmpty() {
                return content.length == 0;
            }

            @Override
            public long getSize() {
                return content.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return content;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(content);
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                FileCopyUtils.copy(content, dest);
            }
        };
    }
}
