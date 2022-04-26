package com.xzh.utils.object;

import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.util.Random;

/**
 * 随机工具
 *
 * @author 向振华
 * @date 2020/07/17 09:35
 */
public class RandomUtils {

    /**
     * 获取[min,max]范围内随机正整数
     *
     * @param min
     * @param max
     * @return
     */
    public static int rangeNum(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 获取[min,max]范围内随机浮点数
     *
     * @param min
     * @param max
     * @param point 小数点后几位
     * @return
     */
    public static double rangeNum(double min, double max, int point) {
        double d = min + ((max - min) * new Random().nextDouble());
        return new BigDecimal(d).setScale(point, BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     * 获取n位数随机正数
     * 首位不为0
     *
     * @param n
     * @return
     */
    public static String num(Integer n) {
        String numeric = RandomStringUtils.randomNumeric(n);
        return !numeric.startsWith("0") ? numeric : num(n);
    }

    /**
     * 获取n位随机汉字
     *
     * @param n
     * @return
     */
    public static String chinese(Integer n) {
        return RandomStringUtils.random(n, 0x4e00, 0x9fa5, false, false);
    }

    /**
     * 获取n位随机字母
     *
     * @param n
     * @return
     */
    public static String alphabetic(Integer n) {
        return RandomStringUtils.randomAlphabetic(n);
    }

    /**
     * 获取n位随机字母数字组合
     *
     * @param n
     * @return
     */
    public static String alphabeticAndNum(Integer n) {
        return RandomStringUtils.randomAlphanumeric(n);
    }
}
