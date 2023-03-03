package com.xzh.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件工具
 *
 * @author: 向振华
 * @date: 2019/11/19 10:00
 */
public class FileUtils {

    /**
     * 根据关键字复制
     *
     * @param sourceFolderDirectory 源文件夹目录
     * @param targetFolderDirectory 目标文件夹目录
     * @param includeKeys           关键字 忽略大小写 不传参时复制所有
     */
    public static void copyByKey(String sourceFolderDirectory, String targetFolderDirectory, String... includeKeys) {
        List<File> fileList = getFileList(sourceFolderDirectory);
        if (fileList == null || fileList.isEmpty()) {
            return;
        }
        for (File file : fileList) {
            boolean flag = true;
            for (String key : includeKeys) {
                if (file.getName().toLowerCase().contains(key.toLowerCase())) {
                    flag = true;
                    break;
                }
                flag = false;
            }
            if (flag) {
                copy(file.getAbsolutePath(), targetFolderDirectory);
            }
        }
    }

    /**
     * 排除关键字复制
     *
     * @param sourceFolderDirectory 源文件夹目录
     * @param targetFolderDirectory 目标文件夹目录
     * @param excludeKeys           排除的关键字 忽略大小写 不传参时复制所有
     */
    public static void copyWithoutKey(String sourceFolderDirectory, String targetFolderDirectory, String... excludeKeys) {
        List<File> fileList = getFileList(sourceFolderDirectory);
        if (fileList == null || fileList.isEmpty()) {
            return;
        }
        for (File file : fileList) {
            boolean flag = true;
            for (String key : excludeKeys) {
                if (file.getName().toLowerCase().contains(key.toLowerCase())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                copy(file.getAbsolutePath(), targetFolderDirectory);
            }
        }
    }

    /**
     * 文件复制
     *
     * @param sourceAbsolutePath    源文件路径
     * @param targetFolderDirectory 目标文件夹目录
     */
    public static void copy(String sourceAbsolutePath, String targetFolderDirectory) {
        makeDirectory(targetFolderDirectory);
        String newAbsolutePath = getNewAbsolutePath(sourceAbsolutePath, targetFolderDirectory);
        try {
            FileInputStream fis = new FileInputStream(sourceAbsolutePath);
            FileOutputStream fos = new FileOutputStream(newAbsolutePath);
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
     * 如果目录不存在则创建目录
     *
     * @param targetFolderDirectory
     */
    public static void makeDirectory(String targetFolderDirectory) {
        String[] folderDirectories = targetFolderDirectory.split("\\\\");
        String temp = "";
        for (String folderDirectory : folderDirectories) {
            temp = temp + File.separator + folderDirectory;
            File dir = new File(temp);
            if (!dir.isDirectory()) {
                // 如果目录不存在则创建
                dir.mkdir();
            }
        }
    }

    /**
     * 删除文件夹（并递归删除文件夹下所有文件）
     *
     * @param file
     */
    public static void deleteDirectory(File file) {
        if (file.isDirectory()) {
            // 得到当前的路径
            String[] childFilePaths = file.list();
            if (childFilePaths == null) {
                return;
            }
            for (String childFilePath : childFilePaths) {
                File childFile = new File(file.getAbsolutePath() + File.separator + childFilePath);
                deleteDirectory(childFile);
            }
        }
        file.delete();
    }

    /**
     * 获取文件夹列表
     *
     * @param sourceFolderDirectory 源文件夹目录
     * @return 返回文件夹列表
     */
    public static List<File> getDirectoryList(String sourceFolderDirectory) {
        List<File> fileList = new ArrayList<>();
        File dir = new File(sourceFolderDirectory);
        // 该文件目录下文件全部放入数组
        File[] files = dir.listFiles();
        if (files == null) {
            return null;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                fileList.add(file);
                List<File> innerFileList = getDirectoryList(file.getAbsolutePath());
                if (innerFileList != null && !innerFileList.isEmpty()) {
                    fileList.addAll(innerFileList);
                }
            }
        }
        return fileList;
    }

    /**
     * 获取文件列表
     *
     * @param sourceFolderDirectory 源文件夹目录
     * @return 返回文件列表
     */
    public static List<File> getFileList(String sourceFolderDirectory) {
        List<File> fileList = new ArrayList<>();
        File dir = new File(sourceFolderDirectory);
        // 该文件目录下文件全部放入数组
        File[] files = dir.listFiles();
        if (files == null) {
            return null;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                List<File> innerFileList = getFileList(file.getAbsolutePath());
                if (innerFileList != null && !innerFileList.isEmpty()) {
                    fileList.addAll(innerFileList);
                }
            } else if (file.isFile()) {
                fileList.add(file);
            }
        }
        return fileList;
    }

    /**
     * 获取当前目录文件列表
     *
     * @param sourceFolderDirectory 源文件夹目录
     * @return 返回文件列表
     */
    public static List<File> getCurrentFileList(String sourceFolderDirectory) {
        List<File> fileList = new ArrayList<>();
        File dir = new File(sourceFolderDirectory);
        // 该文件目录下文件全部放入数组
        File[] files = dir.listFiles();
        if (files == null) {
            return fileList;
        }
        for (File file : files) {
            if (file.isFile()) {
                fileList.add(file);
            }
        }
        return fileList;
    }

    /**
     * 获取文件名列表（含扩展名）
     *
     * @param sourceFolderDirectory 源文件夹目录
     * @return 返回文件名列表
     */
    public static List<String> getFileNameList(String sourceFolderDirectory) {
        List<File> fileList = getFileList(sourceFolderDirectory);
        return fileList != null ? fileList.stream().map(File::getName).collect(Collectors.toList()) : new ArrayList<>();
    }

    /**
     * 生成新的文件名
     *
     * @param sourceAbsolutePath
     * @param targetFolderDirectory
     * @return
     */
    public static String getNewAbsolutePath(String sourceAbsolutePath, String targetFolderDirectory) {
        int n = 1;
        String[] names = getFileNames(sourceAbsolutePath);
        String newAbsolutePath = targetFolderDirectory + File.separator + names[0];
        // 循环找到不重复的文件名
        while (new File(newAbsolutePath).isFile()) {
            newAbsolutePath = targetFolderDirectory + File.separator + names[1] + "_" + n++ + names[2];
        }
        return newAbsolutePath;
    }

    /**
     * 将绝对路径拆分成 全名 + 文件名 + 文件扩展名 的数组
     * eg: "D:\\123.jpg" ---> ["123.jpg", "123", ".jpg"]
     *
     * @param sourceAbsolutePath
     * @return
     */
    public static String[] getFileNames(String sourceAbsolutePath) {
        String fileName = sourceAbsolutePath.substring(sourceAbsolutePath.lastIndexOf(File.separator) + 1);
        int i = fileName.lastIndexOf(".");
        if (i != -1) {
            return new String[]{fileName, fileName.substring(0, i), fileName.substring(i)};
        } else {
            return new String[]{fileName, fileName, ""};
        }
    }
}
