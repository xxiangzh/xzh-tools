package com.xzh.utils;

import java.text.DecimalFormat;

/**
 * @author 向振华
 * @date 2019/05/05 15:56
 */
public class NumberUtils {

    /**
     * 保留两位小数
     *
     * @param number
     * @return
     */
    public static Double twoPoint(Double number) {
        return Double.valueOf(new DecimalFormat("######0.00").format(number));
    }

    /**
     * 保留两位小数
     *
     * @param number
     * @return
     */
    public static String twoPoint(String number) {
        return new DecimalFormat("######0.00").format(Double.valueOf(number));
    }
}
