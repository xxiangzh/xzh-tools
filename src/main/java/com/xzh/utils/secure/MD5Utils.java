package com.xzh.utils.secure;

import cn.hutool.crypto.SecureUtil;

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
        return SecureUtil.md5(text);
    }
}
