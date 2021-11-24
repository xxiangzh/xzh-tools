package com.xzh.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具
 *
 * @author 向振华
 * @date 2020/08/25 16:25
 */
@Slf4j
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

    /**
     * 字符串根据字符位截取
     *
     * @param string
     * @param charsetName
     * @param beginIndex
     * @param endIndex
     * @return
     */
    public static String sub(String string, String charsetName, int beginIndex, int endIndex) {
        try {
            byte[] stringBytes = string.getBytes(charsetName);
            byte[] subStr = new byte[endIndex - beginIndex];
            int n = 0;
            for (int i = beginIndex; i < endIndex; i++) {
                subStr[n++] = stringBytes[i];
            }
            return new String(subStr, charsetName);
        } catch (Exception e) {
            log.error("字符截取异常：" + string, e);
            return "";
        }
    }
}
