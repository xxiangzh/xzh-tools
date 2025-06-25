package com.xzh.utils.date;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author 向振华
 * @date 2025/06/25 15:46
 */
public class DayUtils {

    private static final ZoneId SYSTEM_ZONE = ZoneId.systemDefault();

    /**
     * 获取当天的开始时间
     *
     * @param date 输入日期
     * @return "yyyy-MM-dd 00:00:00.000"
     */
    public static Date getStartDay(Date date) {
        if (date == null) {
            return null;
        }
        return Date.from(
                date.toInstant()
                        .atZone(SYSTEM_ZONE)
                        .toLocalDate()
                        .atStartOfDay(SYSTEM_ZONE)
                        .toInstant()
        );
    }

    /**
     * 获取当天的结束时间
     *
     * @param date 输入日期
     * @return "yyyy-MM-dd 23:59:59.999"
     */
    public static Date getEndDay(Date date) {
        if (date == null) {
            return null;
        }
        return Date.from(
                date.toInstant()
                        .atZone(SYSTEM_ZONE)
                        .toLocalDate()
                        .atTime(LocalTime.MAX)
                        .atZone(SYSTEM_ZONE)
                        .toInstant()
        );
    }
}
