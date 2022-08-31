package com.xzh.utils.file;

import java.io.File;

/**
 * 文件工具
 *
 * @author: 向振华
 * @date: 2019/11/19 10:00
 */
public class FileNameUtils {

    /**
     * 重命名
     *
     * @param file
     * @param newName          新的文件名（不含文件扩展名），为空时用旧文件名
     * @param newExtensionName 新的文件扩展名（文件格式.后缀），为空时用旧文件扩展名
     */
    public static void rename(File file, String newName, String newExtensionName) {
        // 文件夹路径
        String parent = file.getParent();
        // 文件名
        String fileName = file.getName();
        // 文件真实名（不含扩展名）
        String realName = newName != null ? newName : fileName.substring(0, fileName.lastIndexOf("."));
        // 文件扩展名
        String extensionName = newExtensionName != null ? newExtensionName : fileName.substring(fileName.lastIndexOf("."));
        // 路径名 = 文件夹路径 + 新名字 + 文件扩展名
        String pathname = parent + File.separator + realName + extensionName;
        // 重命名
        file.renameTo(new File(pathname));
    }

    /**
     * 重命名拼接旧名字
     *
     * @param file
     * @param newName
     */
    public static void renameSpliceOldName(File file, String newName) {
        // 文件夹路径
        String parent = file.getParent();
        // 文件名
        String name = file.getName();
        // 路径名 = 文件夹路径 + 新名字 + 旧名字（含文件扩展名）
        String pathname = parent + File.separator + newName + name;
        // 重命名
        file.renameTo(new File(pathname));
    }
}
