package com.xzh.utils;


import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;

import static java.util.regex.Pattern.compile;

/**
 * 校验类
 *
 * @author: 向振华
 * @date: 2019/10/09 13:56
 */
public class RegexUtils {

    /**
     * 是否是身份证（中国大陆18/15位身份证）
     *
     * @param idCard
     * @return
     */
    public static boolean isIdCard(String idCard) {
        if (StringUtils.isBlank(idCard)) {
            return false;
        }
        return idCard.matches("(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)");
    }

    /**
     * 是否是手机号
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return false;
        }
        return mobile.matches("[1][3456789]\\d{9}");
    }

    /**
     * 是否有效邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        return email.matches("^([a-zA-Z0-9])+([a-zA-Z0-9_.-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$");
    }

    /**
     * 密码是否符合规范
     * 1.必须只能是 大写字母、小写字母和数字构成的密码
     * 2.大写字母、小写字母、数字都至少出现一次
     *
     * @param password
     * @return
     */
    public static boolean isValidPassword(String password) {
        if (StringUtils.isBlank(password)) {
            return false;
        }
        if (password.matches("\\w+")) {
            Matcher m = compile("[a-z]+").matcher(password);
            if (!m.find()) {
                return false;
            } else {
                m.reset().usePattern(compile("[A-Z]+"));
                if (!m.find()) {
                    return false;
                } else {
                    m.reset().usePattern(compile("[0-9]+"));
                    if (!m.find()) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        } else {
            return false;
        }
    }

    /**
     * 是否正整数
     *
     * @param number
     * @return
     */
    public static boolean isPositiveInt(String number) {
        if (StringUtils.isBlank(number)) {
            return false;
        }
        return number.matches("^\\+?[1-9][0-9]*$");
    }

    /**
     * 是否正浮点数
     *
     * @param number
     * @return
     */
    public static boolean isPositivePoint(String number) {
        if (StringUtils.isBlank(number)) {
            return false;
        }
        return number.matches("^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
    }
}