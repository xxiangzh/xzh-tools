package com.xzh.utils;

import java.math.BigDecimal;

/**
 * 精确运算
 *
 * @author 向振华
 * @date 2019/05/13 11:52
 */
public class Arith {

    /**
     * 加法运算。
     *
     * @param s1 被加数
     * @param s2 加数
     * @return 两个参数的和
     */
    public static double add(double s1, double s2) {
        BigDecimal b1 = new BigDecimal(Double.toString(s1));
        BigDecimal b2 = new BigDecimal(Double.toString(s2));
        return b1.add(b2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 减法运算。
     *
     * @param s1 被减数
     * @param s2 减数
     * @return 两个参数的差
     */
    public static double subtract(double s1, double s2) {
        BigDecimal b1 = new BigDecimal(Double.toString(s1));
        BigDecimal b2 = new BigDecimal(Double.toString(s2));
        return b1.subtract(b2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 乘法运算。
     *
     * @param s1 被乘数
     * @param s2 乘数
     * @return 两个参数的积
     */
    public static double multiply(double s1, double s2) {
        BigDecimal b1 = new BigDecimal(Double.toString(s1));
        BigDecimal b2 = new BigDecimal(Double.toString(s2));
        return b1.multiply(b2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 除法运算。
     * 由scale参数指定精度四舍五入。
     *
     * @param s1    被除数
     * @param s2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double divide(double s1, double s2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("精确度不能小于0！");
        }
        if (s2 == 0) {
            return 0;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(s1));
        BigDecimal b2 = new BigDecimal(Double.toString(s2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("精确度不能小于0！");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
