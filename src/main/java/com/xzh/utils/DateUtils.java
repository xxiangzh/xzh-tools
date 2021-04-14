package com.xzh.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具
 *
 * @author: 向振华
 * @date: 2019/12/23 17:54
 */
@Slf4j
public class DateUtils {

    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_SHORT = "yyyyMMddHHmmss";
    public static final String DATE = "yyyy-MM-dd";
    public static final String TIME = "HH:mm:ss";
    public static final String CRON = "ss mm HH dd MM ? yyyy";

    /**
     * 日期转字符串
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        return dateToString(date, null);
    }

    /**
     * 日期转字符串，根据pattern转换
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String dateToString(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        if (pattern == null || pattern.length() == 0) {
            pattern = DATE_TIME;
        }
        try {
            return new SimpleDateFormat(pattern).format(date);
        } catch (Exception e) {
            log.error("日期转换出错：", e);
            return null;
        }
    }

    /**
     * 字符串转日期
     *
     * @param dateString
     * @return
     */
    public static Date stringToDate(String dateString) {
        return stringToDate(dateString, null);
    }

    /**
     * 字符串转日期，根据pattern转换
     *
     * @param dateString
     * @param pattern
     * @return
     */
    public static Date stringToDate(String dateString, String pattern) {
        if (dateString == null || dateString.length() == 0) {
            return null;
        }
        if (pattern == null || pattern.length() == 0) {
            pattern = DATE_TIME;
        }
        try {
            return new SimpleDateFormat(pattern).parse(dateString);
        } catch (Exception e) {
            log.error("日期转换出错：", e);
            return null;
        }
    }

    /**
     * 字符串转字符串，根据pattern转换
     *
     * @param dateString
     * @param oldPattern
     * @param newPattern
     * @return
     */
    public static String stringToString(String dateString, String oldPattern, String newPattern) {
        Date date = stringToDate(dateString, oldPattern);
        return dateToString(date, newPattern);
    }

    /**
     * 日期转日期，根据pattern转换
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Date dateToDate(Date date, String pattern) {
        String string = dateToString(date, pattern);
        return stringToDate(string, pattern);
    }
}