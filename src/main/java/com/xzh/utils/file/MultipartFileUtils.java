package com.xzh.utils.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * @author 向振华
 * @date 2020/08/31 14:44
 */
public class MultipartFileUtils {

    /**
     * 文件转换
     *
     * @param multipartFile
     * @return
     */
    public static File convertToFile(MultipartFile multipartFile) {
        // 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件后缀
        assert fileName != null;
        String prefix = fileName.substring(fileName.lastIndexOf('.'));
        // 用uuid作为文件名，防止生成的临时文件重复
        String uuid = UUID.randomUUID().toString().replace("-", "");
        File file = null;
        try {
            file = File.createTempFile(uuid, prefix);
            // MultipartFile to File
            multipartFile.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
}
