package com.xzh.utils.encode;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 * AES加密工具
 *
 * @author: 向振华
 * @date: 2020/09/08 09:31
 */
public class AesUtils {

    /**
     * 加密
     * ECB/PKCS7/128bits
     *
     * @param secretKey 密钥
     * @param plainText 明文
     * @return
     */
    public static String encrypt(String secretKey, String plainText) {
        // AES加密
        byte[] aesEncrypt = new SymmetricCrypto(SymmetricAlgorithm.AES, secretKey.getBytes()).encrypt(plainText);
        // Base64编码
        return Base64.encode(aesEncrypt);
    }

    /**
     * 解密
     * ECB/PKCS7/128bits
     *
     * @param secretKey  密钥
     * @param cipherText 密文
     * @return
     */
    public static String decrypt(String secretKey, String cipherText) {
        // Base64解码
        byte[] base64Decode = Base64.decode(cipherText);
        // AES解密
        byte[] aesDecrypt = new SymmetricCrypto(SymmetricAlgorithm.AES, secretKey.getBytes()).decrypt(base64Decode);
        return new String(aesDecrypt);
    }
}