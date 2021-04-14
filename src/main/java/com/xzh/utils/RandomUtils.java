package com.xzh.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * 随机工具
 *
 * @author 向振华
 * @date 2020/07/17 09:35
 */
public class RandomUtils {

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
}
