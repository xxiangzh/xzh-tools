package com.xzh.utils.file;

import java.io.File;

/**
 * 文件重命名工具
 *
 * @author: 向振华
 * @date: 2019/11/19 10:00
 */
public class FileNameUtils {

    /**
     * 添加前缀
     *
     * @param file
     * @param prefix
     */
    public static void addPrefix(File file, String prefix) {
        String[] fileNames = FileUtils.splitFileName(file.getAbsolutePath());
        fileNames[1] = prefix + fileNames[1];
        String newAbsolutePath = fileNames[0] + fileNames[1] + fileNames[2];
        // 重命名
        file.renameTo(new File(newAbsolutePath));
    }

    /**
     * 替换名称
     *
     * @param file
     * @param target 需要修改的字符串
     * @param replacement 替换成什么
     */
    public static void replace(File file, String target, String replacement) {
        String[] fileNames = FileUtils.splitFileName(file.getAbsolutePath());
        fileNames[1] = fileNames[1].replace(target, replacement);
        String newAbsolutePath = fileNames[0] + fileNames[1] + fileNames[2];
        // 重命名
        file.renameTo(new File(newAbsolutePath));
    }

    /**
     * 重命名
     *
     * @param file
     * @param newName          新的文件名（不含文件扩展名），为空时用旧文件名
     * @param newExtensionName 新的文件扩展名（文件格式.后缀），为空时用旧文件扩展名
     */
    public static void rename(File file, String newName, String newExtensionName) {
        String[] fileNames = FileUtils.splitFileName(file.getAbsolutePath());
        fileNames[1] = newName != null ? newName : fileNames[1];
        fileNames[2] = newExtensionName != null ? newExtensionName : fileNames[2];
        String newAbsolutePath = fileNames[0] + fileNames[1] + fileNames[2];
        // 重命名
        file.renameTo(new File(newAbsolutePath));
    }
}