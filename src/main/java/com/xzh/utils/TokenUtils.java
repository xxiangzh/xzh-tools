package com.xzh.utils;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Token工具
 *
 * @author 向振华
 * @date 2020/12/16 09:48
 */
@Slf4j
public class TokenUtils {

    private static final String JWT_SIGNING = "xiangzhenhua";
    private static final Long TOKEN_EXPIRE_TIME = 60000L;

    /**
     * 创建token
     *
     * @param json
     * @return
     */
    public static String build(String json) {
        return Jwts.builder()
                .setSubject(json)
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS512, JWT_SIGNING)
                .compact();
    }

    /**
     * 将token解析成实体类
     *
     * @param token
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T get(String token, Class<T> clazz) {
        if (StringUtils.isBlank(token)) {
            throw new RuntimeException("Token为空");
        }
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(JWT_SIGNING)
                    .parseClaimsJws(token)
                    .getBody();
            return JSON.parseObject(claims.getSubject(), clazz);
        } catch (ExpiredJwtException e) {
            log.error("JWT过期：", e);
            throw new RuntimeException("JWT过期");
        } catch (UnsupportedJwtException e) {
            log.error("不支持的JWT：", e);
            throw new RuntimeException("不支持的JWT");
        } catch (MalformedJwtException e) {
            log.error("JWT格式错误：", e);
            throw new RuntimeException("JWT格式错误");
        } catch (SignatureException e) {
            log.error("签名异常：", e);
            throw new RuntimeException("签名异常");
        } catch (IllegalArgumentException e) {
            log.error("非法请求：", e);
            throw new RuntimeException("非法请求");
        } catch (Exception e) {
            log.error("解析异常：", e);
            throw new RuntimeException("解析异常");
        }
    }
}