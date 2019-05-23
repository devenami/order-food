package com.domain.food.utils;

import org.junit.Test;

public class DigestUtilTest {

    @Test
    public void testMd5() {
        String text = "this is a text";
        String md5 = DigestUtil.md5(text);
        System.out.println(md5);
    }

    @Test
    public void testBinary2Decimal() {
        // 150
        System.out.println(DigestUtil.binary2Decimal("10010110"));
    }

    @Test
    public void testDecimal2Binary() {
        //10010110
        System.out.println(DigestUtil.decimal2Binary(150));
    }

}