package com.xzh.utils.file;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.xzh.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具
 *
 * @author 向振华
 * @date 2020/08/19 14:08
 */
public class MetadataUtils {

    /**
     * 获取拍摄日期
     *
     * @return
     */
    public static String getDateTime(File file, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            return null;
        }
        List<Tag> tagList = getTagList(file);
        if (CollectionUtils.isEmpty(tagList)) {
            return null;
        }
        for (Tag tag : tagList) {
            int tagType = tag.getTagType();
            String directoryName = tag.getDirectoryName();
            String tagName = tag.getTagName();
            String description = tag.getDescription();
            // jpeg
            if ("Exif IFD0".equals(directoryName) && "Date/Time".equals(tagName)) {
                return DateUtils.stringToString(description, "yyyy:MM:dd HH:mm:ss", pattern);
            }
            // mp4
            else if ("MP4 Video".equals(directoryName) && "Creation Time".equals(tagName)) {
                return DateUtils.stringToString(description, "E MMM dd HH:mm:ss +08:00 yyyy", pattern);
            }
            // mov
            else if ("QuickTime Video".equals(directoryName) && "Creation Time".equals(tagName)) {
                return DateUtils.stringToString(description, "E MMM dd HH:mm:ss +08:00 yyyy", pattern);
            }
        }
        return null;
    }

    /**
     * 获取所有标签
     *
     * @param file
     * @return
     */
    public static List<Tag> getTagList(File file) {
        List<Tag> tagList = new ArrayList<>();
        Metadata metadata = null;
        try {
            metadata = ImageMetadataReader.readMetadata(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (metadata != null) {
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    //System.out.println(tag);
                    tagList.add(tag);
                }
            }
        }
        return tagList;
    }
}