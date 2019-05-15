package com.domain.food.utils;

/**
 * 字符串工具类
 *
 * @author zhoutaotao
 * @date 2019/5/15
 */
public class StringUtil {

    /**
     * 判读字符串是否是空串
     */
    public static boolean isBlank(String str) {
        return ObjectUtil.isNull(str) || "".equalsIgnoreCase(str.trim());
    }

}
