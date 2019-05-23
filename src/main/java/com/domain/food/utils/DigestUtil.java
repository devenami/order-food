package com.domain.food.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具
 *
 * @author zhoutaotao
 * @date 2019/5/23
 */
public class DigestUtil {

    // 16 进制编码串
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};


    public static String md5(String source) {
        Condition.notBlank(source, "被加密的数据不能为null");
        try {
            MessageDigest instance = MessageDigest.getInstance("md5");
            byte[] digest = instance.digest(source.getBytes());
            return byteArr2Hex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }


    public static String byteArr2Hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(byte2Hex(aByte));
        }
        return sb.toString();
    }

    private static String byte2Hex(byte b) {
        int n = b;
        if (n < 0) {
            n = n + 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 将二进制字符串反转
     *
     * @param binary 二进制
     * @return 反转后的二进制数组
     */
    private static String[] reverseBinary(String binary) {
        String[] binaryArr = binary.split("");
        // 将数组内部的数据进行反转
        int cycle = binary.length() % 2 == 0 ? binary.length() / 2 : binary.length() / 2 + 1;
        for (int i = 0; i < cycle; i++) {
            String tem = binaryArr[i];
            binaryArr[i] = binaryArr[binary.length() - 1 - i];
            binaryArr[binary.length() - 1 - i] = tem;
        }
        return binaryArr;
    }

    /**
     * 将二进制数字符串，转换为十进制
     *
     * @param binary 二进制字符串
     * @return 十进制数字
     */
    public static int binary2Decimal(String binary) {
        Condition.notBlank(binary, "input must not null");
        String[] binaryArr = reverseBinary(binary);
        int num = 0;
        for (int i = 0; i < binaryArr.length; i++) {
            num += Integer.parseInt(binaryArr[i]) * Math.pow(2, i);
        }
        return num;
    }

    /**
     * 将十进制转换为二进制
     *
     * @param decimal 十进制
     * @return 二进制
     */
    public static String decimal2Binary(int decimal) {
        StringBuilder sb = new StringBuilder();
        div2(sb, decimal);
        return sb.reverse().toString();
    }

    /**
     * 指定数字进行除2, 取模
     *
     * @param sb  字符串拼接
     * @param num 被除的数值
     */
    private static void div2(StringBuilder sb, int num) {
        int m = num % 2;
        sb.append(m);
        num = num / 2;
        if (num != 0) {
            div2(sb, num);
        }
    }


}
