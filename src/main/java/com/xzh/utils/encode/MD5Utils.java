package com.xzh.utils.encode;

import cn.hutool.crypto.digest.MD5;

/**
 * MD5
 *
 * @author 向振华
 * @date 2022/08/23 10:42
 */
public class MD5Utils {

    /**
     * 加密
     *
     * @param text
     * @return
     */
    public static String encode(String text) {
        return MD5.create().digestHex(text);
    }
}
