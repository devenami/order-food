package com.domain.food.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期工具类
 *
 * @author feb13th
 * @since 2019/5/16 0:27
 */
public class DateUtil {

    public static final String FORMAT_DATA_SIMPLE = "yyyymmdd";
    public static final String FORMAT_DATE = "yyyy-mm-dd";
    public static final String FORMAT_DATETIME = "yyyy-mm-dd HH:MM:ss";

    public static String formatDate(LocalDate date) {
        return formatDate(date, FORMAT_DATE);
    }

    public static String formatDate(LocalDate date, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return date.format(dateTimeFormatter);
    }

    public static String formatDateTime(LocalDateTime datetime) {
        return formatDateTime(datetime, FORMAT_DATETIME);
    }

    public static String formatDateTime(LocalDateTime dateTime, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return dateTime.format(dateTimeFormatter);
    }


}
