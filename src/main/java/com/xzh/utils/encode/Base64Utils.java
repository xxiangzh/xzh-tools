package com.xzh.utils.encode;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Base64
 *
 * @author: 向振华
 * @date: 2019/11/21 13:38
 */
public class Base64Utils {

    /**
     * 加密
     *
     * @param text 明文
     * @return 密文
     */
    public static String encoder(String text) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] textByte = text.getBytes(StandardCharsets.UTF_8);
        return encoder.encodeToString(textByte);
    }

    /**
     * 解密
     *
     * @param text 密文
     * @return 明文
     */
    public static String decoder(String text) {
        Base64.Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(text), StandardCharsets.UTF_8);
    }
}
