package com.xzh.utils.secure;

import java.util.Base64;

/**
 * Base64
 *
 * @author: 向振华
 * @date: 2019/11/21 13:38
 */
public class Base64Utils {

    /**
     * 编码
     *
     * @param text
     * @return
     */
    public static String encode(byte[] text) {
        return Base64.getEncoder().encodeToString(text);
    }

    /**
     * 解码
     *
     * @param text
     * @return
     */
    public static byte[] decode(String text) {
        return Base64.getDecoder().decode(text);
    }
}
