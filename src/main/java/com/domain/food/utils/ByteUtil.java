package com.domain.food.utils;

import java.util.Arrays;
import java.util.List;

/**
 * 处理字节数据
 *
 * @author feb13th
 * @since 2019/5/23 23:50
 */
public class ByteUtil {

    private static final List<String> HEX_DIGITS = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F");

    /**
     * 指定数字进行除2, 取模
     *
     * @param sb     字符串拼接
     * @param num    被除的数值
     * @param factor 被除数
     */
    private static void divFactor(StringBuilder sb, long num, int factor) {
        long m = num % factor;
        sb.append(m);
        num = num / factor;
        if (num != 0) {
            divFactor(sb, num, factor);
        }
    }

    /**
     * 将字符串按指定数量进行分组补齐
     *
     * @param binary 二进制
     * @param count  指定数量为一组
     * @return 补0后的数组
     */
    private static String[] format(String binary, int count) {
        int l = binary.length() % count;
        if (l == 0) {
            return binary.split("");
        }
        StringBuilder result = new StringBuilder(binary);
        for (int i = 0; i < count - l; i++) {
            result.insert(0, "0");
        }
        return result.toString().split("");
    }

    /**
     * 在字符串前补零使其达到指定长度
     *
     * @param binary 二进制字符串
     * @param length 指定的长度
     * @return 补零后的字符串
     */
    private static String appendTo(String binary, int length) {
        if (binary.length() == length) {
            return binary;
        }
        StringBuilder result = new StringBuilder(binary);
        for (int i = 0; i < length - binary.length(); i++) {
            result.insert(0, "0");
        }
        return result.toString();
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param bytes 字节数据
     * @return 十六进制字符串
     */
    public static String byte2Hex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            int n = b;
            if (n < 0) {
                n += 256;
            }
            result.append(HEX_DIGITS.get(n / 16)).append(HEX_DIGITS.get(n % 16));
        }
        return result.toString();
    }

    /**
     * 将二进制数字符串，转换为十进制
     *
     * @param binary 二进制字符串
     * @return 十进制数字
     */
    public static int binary2Decimal(String binary) {
        Condition.notBlank(binary, "input must not null");
        String[] arr = binary.split("");
        int result = 0;
        for (int i = arr.length - 1, factor = 0; i >= 0; i--, factor++) {
            result += Integer.parseInt(arr[i]) * Math.pow(2, factor);
        }
        return result;
    }

    /**
     * 将十进制转换为二进制
     *
     * @param decimal 十进制
     * @return 二进制
     */
    public static String decimal2Binary(long decimal) {
        StringBuilder sb = new StringBuilder();
        divFactor(sb, decimal, 2);
        return sb.reverse().toString();
    }

    /**
     * 二进制转八进制
     *
     * @param binary 二进制
     * @return 八进制
     */
    public static String binary2Octal(String binary) {
        StringBuilder result = new StringBuilder();
        String[] arr = format(binary, 3);
        for (int i = 0; i < arr.length; i += 3) {
            int one = Integer.parseInt(arr[i]);
            int two = Integer.parseInt(arr[i + 1]);
            int three = Integer.parseInt(arr[i + 2]);
            result.append((one << 2) + (two << 1) + three);
        }

        return result.toString();
    }

    /**
     * 八进制转二进制
     *
     * @param octal 八进制
     * @return 二进制
     */
    public static String octal2Binary(String octal) {
        Condition.notBlank(octal, "input must not null");
        StringBuilder result = new StringBuilder();
        String[] arr = octal.split("");
        for (String o : arr) {
            String binary = decimal2Binary(Integer.parseInt(o));
            // 补足三位一组
            for (int i = 0; i < 3 - binary.length(); i++) {
                binary = "0".concat(binary);
            }
            result.append(binary);
        }
        return result.substring(result.indexOf("1"));
    }

    /**
     * 二进制转十六进制
     *
     * @param binary 二进制
     * @return 十六进制
     */
    public static String binary2Hex(String binary) {
        Condition.notBlank(binary, "input must not null");
        StringBuilder result = new StringBuilder();
        String[] arr = format(binary, 4);
        for (int i = 0; i < arr.length; i += 4) {
            int one = Integer.parseInt(arr[i]);
            int two = Integer.parseInt(arr[i + 1]);
            int three = Integer.parseInt(arr[i + 2]);
            int four = Integer.parseInt(arr[i + 3]);
            result.append(HEX_DIGITS.get((one << 3) + (two << 2) + (three << 1) + four));
        }
        return result.toString();
    }

    /**
     * 十六进制转二进制
     *
     * @param hex 十六进制
     * @return 二进制
     */
    public static String hex2Binary(String hex) {
        Condition.notBlank(hex, "input must not null");
        StringBuilder result = new StringBuilder();
        String[] arr = hex.toUpperCase().split("");
        for (String o : arr) {
            int index = HEX_DIGITS.indexOf(o);
            if (index == -1) {
                throw new IllegalArgumentException("illegal hex string");
            }
            String binary = decimal2Binary(index);
            result.append(appendTo(binary, 4));
        }
        return result.substring(result.indexOf("1"));
    }

    /**
     * 十进制转八进制
     *
     * @param decimal 十进制
     * @return 八进制
     */
    public static String decimal2Octal(long decimal) {
        StringBuilder sb = new StringBuilder();
        divFactor(sb, decimal, 8);
        return sb.reverse().toString();
    }

    /**
     * 将八进制转换为十进制
     *
     * @param octal 八进制
     * @return 十进制
     */
    public static long octal2Decimal(String octal) {
        Condition.notBlank(octal, "input must not null");
        String[] arr = octal.split("");
        long result = 0;
        for (int i = arr.length - 1, factor = 0; i >= 0; i--, factor++) {
            result += Long.parseLong(arr[i]) * Math.pow(8, factor);
        }
        return result;
    }

    /**
     * 十进制转十六进制
     *
     * @param decimal 十进制
     * @return 十六进制
     */
    public static String decimal2Hex(long decimal) {
        StringBuilder sb = new StringBuilder();
        divFactor(sb, decimal, 16);
        return sb.reverse().toString();
    }

    /**
     * 十六进制转十进制
     *
     * @param hex 十六进制
     * @return 十进制
     */
    public static long hex2Decimal(String hex) {
        Condition.notBlank(hex, "input must not null");
        String[] arr = hex.toUpperCase().split("");
        long result = 0;
        for (int i = arr.length - 1, factor = 0; i >= 0; i--, factor++) {
            int index = HEX_DIGITS.indexOf(arr[i]);
            if (index == -1) {
                throw new IllegalArgumentException("illegal hex string");
            }
            result += index * Math.pow(16, factor);
        }
        return result;
    }

}
