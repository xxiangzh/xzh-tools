package com.xzh.utils.object;

import org.apache.commons.lang3.RandomStringUtils;

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
    public static int randomRange(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 获取n位数随机数
     *
     * @param n
     * @return
     */
    public static String code(Integer n) {
        String numeric = RandomStringUtils.randomNumeric(n);
        return !numeric.startsWith("0") ? numeric : code(n);
    }

    /**
     * 获取n位随机汉字
     *
     * @param n
     * @return
     */
    private static String chinese(Integer n) {
        return RandomStringUtils.random(n, 0x4e00, 0x9fa5, false, false);
    }
}
