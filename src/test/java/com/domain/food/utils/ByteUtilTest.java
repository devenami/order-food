package com.domain.food.utils;

import org.junit.Test;

/**
 * 测试字节工具类
 *
 * @author feb13th
 * @since 2019/5/23 23:55
 */
public class ByteUtilTest {
    @Test
    public void testBinary2Decimal() {
        // 150
        System.out.println(ByteUtil.binary2Decimal("10010110"));
    }

    @Test
    public void testDecimal2Binary() {
        //10010110
        System.out.println(ByteUtil.decimal2Binary(150));
    }

    @Test
    public void testBinary2Octal() {
        //226
        System.out.println(ByteUtil.binary2Octal("10010110"));
    }

    @Test
    public void testOctal2Binary() {
        // 10010110
        System.out.println(ByteUtil.octal2Binary("226"));
    }

    @Test
    public void testBinary2Hex() {
        // AF14C
        System.out.println(ByteUtil.binary2Hex("10101111000101001100"));
    }

    @Test
    public void testHex2Binary() {
        // 10101111000101001100
        System.out.println(ByteUtil.hex2Binary("af14c"));
    }

    @Test
    public void testDecimal2Octal() {
        // 226
        System.out.println(ByteUtil.decimal2Octal(150));
    }

    @Test
    public void testOctal2Decimal() {
        // 150
        System.out.println(ByteUtil.octal2Decimal("226"));
    }

    @Test
    public void testDecimal2Hex() {
        // 96
        System.out.println(ByteUtil.decimal2Hex(150));
    }

    @Test
    public void testHex2Decimal() {
        // 150
        System.out.println(ByteUtil.hex2Decimal("96"));
    }
}