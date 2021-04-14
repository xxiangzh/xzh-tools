package com.xzh.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 向振华
 * @date 2021/01/26 18:08
 */
public class Test5 {

    public static void main(String[] args) {
        Date date = dateToStart(new Date());
        System.out.println(date);
        System.out.println(DateUtils.dateToString(date));
        System.out.println("=============");

        Date date2 = dateToEnd(new Date());
        System.out.println(date2);
        System.out.println(DateUtils.dateToString(date2));
        System.out.println("=============");
    }

    public static Date dateToStart(Date date) {
        if (date == null) {
            return null;
        }
        try {
            String format = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(date);
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date dateToEnd(Date date) {
        if (date == null) {
            return null;
        }
        try {
            String format = new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(date);
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format);
        } catch (Exception e) {
            return null;
        }
    }
}
