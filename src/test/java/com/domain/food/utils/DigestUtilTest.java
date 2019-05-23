package com.domain.food.utils;

import org.junit.Test;

public class DigestUtilTest {

    @Test
    public void testMd5() {
        String text = "this is a text";
        String md5 = DigestUtil.md5(text);
        System.out.println(md5);
    }

}