package com.xzh.utils.encode;

import org.apache.commons.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 密钥对工具
 *
 * @author 向振华
 * @date 2021/11/10 11:03
 */
public class KeyUtils {

    /**
     * 获取私钥
     *
     * @param privateKeyString Base64的私钥字符串
     * @return
     */
    public static PrivateKey getPrivateKey(String privateKeyString) {
        byte[] bytes = Base64.decodeBase64(privateKeyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        PrivateKey privateKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    /**
     * 获取公钥
     *
     * @param publicKeyString Base64的公钥字符串
     * @return
     */
    public static PublicKey getPublicKey(String publicKeyString) {
        byte[] bytes = Base64.decodeBase64(publicKeyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
        PublicKey publicKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicKey;
    }
}
