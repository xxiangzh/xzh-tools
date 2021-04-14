package com.xzh.utils.file;

import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件工具
 *
 * @author: 向振华
 * @date: 2019/11/19 10:00
 */
public class FileUtils {

    private static List<File> fileList = new ArrayList<File>();

    /**
     * 根据关键字复制
     *
     * @param sourceFolderDirectory 源文件夹目录
     * @param targetFolderDirectory 目标文件夹目录
     * @param key                   关键字 （为null或""时 复制所有）
     */
    public static void copyByKey(String sourceFolderDirectory, String targetFolderDirectory, String key) {
        List<File> fileList = getFileList(sourceFolderDirectory, true);
        if (fileList == null) {
            return;
        }
        for (File file : fileList) {
            if (key != null && !file.getName().contains(key)) {
                continue;
            }
            copy(file.getAbsolutePath(), targetFolderDirectory);
        }
    }

    /**
     * 排除关键字复制
     *
     * @param sourceFolderDirectory 源文件夹目录
     * @param targetFolderDirectory 目标文件夹目录
     * @param keys                  关键字数组
     */
    public static void copyWithoutKey(String sourceFolderDirectory, String targetFolderDirectory, String... keys) {
        if (keys == null) {
            return;
        }
        List<File> fileList = getFileList(sourceFolderDirectory, true);
        if (fileList == null) {
            return;
        }
        boolean flag = true;
        for (File file : fileList) {
            for (String key : keys) {
                if (file.getName().toLowerCase().endsWith(key.toLowerCase())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                copy(file.getAbsolutePath(), targetFolderDirectory);
            } else {
                flag = true;
            }
        }
    }

    /**
     * 文件复制
     *
     * @param absolutePath          源文件路径
     * @param targetFolderDirectory 目标文件夹目录
     */
    public static void copy(String absolutePath, String targetFolderDirectory) {
        File dir = new File(targetFolderDirectory);
        if (!dir.isDirectory()) {
            // 如果目录不存在则创建
            dir.mkdir();
        }
        String newNamePath = getNewNamePath(absolutePath, targetFolderDirectory);
        try {
            FileInputStream fis = new FileInputStream(absolutePath);
            FileOutputStream fos = new FileOutputStream(newNamePath);
            byte[] datas = new byte[1024 * 8];
            int len;
            while ((len = fis.read(datas)) != -1) {
                fos.write(datas, 0, len);
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重命名
     *
     * @param file
     * @param newName
     */
    public static void rename(File file, String newName) {
        // 文件夹路径
        String parent = file.getParent();
        // 文件名
        String name = file.getName();
        // 文件扩展名
        String extensionName = name.substring(name.lastIndexOf("."));
        // 路径名 = 文件夹路径 + 新名字 + 文件扩展名
        String pathname = parent + File.separator + newName + extensionName;
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

    /**
     * 获取文件列表
     *
     * @param sourceFolderDirectory 源文件夹目录
     * @param isAll                 是否获取全部文件，true获取全部，false只获取路径下的文件，不包括路径下子文件夹的文件
     * @return 返回文件列表
     */
    public static List<File> getFileList(String sourceFolderDirectory, boolean isAll) {
        File dir = new File(sourceFolderDirectory);
        // 该文件目录下文件全部放入数组
        File[] files = dir.listFiles();
        if (files == null) {
            return null;
        }
        for (File file : files) {
            if (file.isDirectory() && isAll) {
                getFileList(file.getAbsolutePath(), true);
            } else if (file.isDirectory()) {
                continue;
            } else {
                fileList.add(file);
            }
        }
        return fileList;
    }

    /**
     * 获取文件名列表 不递归查
     *
     * @param sourceFolderDirectory
     * @return
     */
    public static List<String> getFileNameList(String sourceFolderDirectory) {
        File dir = new File(sourceFolderDirectory);
        // 该文件目录下文件名全部放入数组
        String[] list = dir.list();
        if (list == null) {
            return null;
        }
        return Arrays.stream(list).map(String::toLowerCase).collect(Collectors.toList());
    }

    /**
     * 将绝对路径拆分成 全名 + 文件名 + 文件扩展名 的数组
     *
     * @param absolutePath
     * @return
     */
    public static String[] getFileNames(String absolutePath) {
        String fileName = absolutePath.substring(absolutePath.lastIndexOf(File.separator) + 1);
        int i = fileName.lastIndexOf(".");
        return new String[]{fileName, fileName.substring(0, i), fileName.substring(i)};
    }

    /**
     * 生成新的文件名
     *
     * @param absolutePath
     * @param targetFolderDirectory
     * @return
     */
    public static String getNewNamePath(String absolutePath, String targetFolderDirectory) {
        String newNamePath;
        String[] names = getFileNames(absolutePath);
        List<String> fileNameList = getFileNameList(targetFolderDirectory);
        if (!CollectionUtils.isEmpty(fileNameList) || fileNameList.contains(names[0].toLowerCase())) {
            newNamePath = targetFolderDirectory + File.separator + getName(fileNameList, names, 1);
        } else {
            newNamePath = targetFolderDirectory + File.separator + names[0];
        }
        return newNamePath;
    }

    /**
     * 找到不重复的文件名
     *
     * @param fileNameList
     * @param names
     * @param n
     * @return
     */
    private static String getName(List<String> fileNameList, String[] names, int n) {
        String name = names[1] + n + names[2];
        if (fileNameList.contains(name.toLowerCase())) {
            return getName(fileNameList, names, ++n);
        }
        return name;
    }
}
