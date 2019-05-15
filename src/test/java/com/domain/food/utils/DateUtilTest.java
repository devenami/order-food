package com.domain.food.utils;

import org.junit.Test;

import java.time.LocalDateTime;

/**
 * 日期工具测试类
 *
 * @author feb13th
 * @since 2019/5/16 0:30
 */
public class DateUtilTest {

    @Test
    public void testFormatDateTime() {
        System.out.println(DateUtil.formatDateTime(LocalDateTime.now()));
    }

}