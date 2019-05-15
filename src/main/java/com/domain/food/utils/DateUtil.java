package com.domain.food.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期工具类
 *
 * @author feb13th
 * @since 2019/5/16 0:27
 */
public class DateUtil {

    public static final String FORMAT_DATETIME = "yyyy-mm-dd HH:MM:ss";

    public static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMAT_DATETIME);
        return dateTime.format(dateTimeFormatter);
    }

}
