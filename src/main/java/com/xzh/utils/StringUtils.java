package com.xzh.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具
 *
 * @author 向振华
 * @date 2020/08/25 16:25
 */
public class StringUtils {

    /**
     * 子字符串数量
     *
     * @param string
     * @param childString
     * @return
     */
    public static int childCount(String string, String childString) {
        Pattern p = Pattern.compile(childString, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        int count = 0;
        while (m.find()) {
            count++;
        }
        return count;
    }
}
