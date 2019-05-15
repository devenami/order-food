package com.domain.food.utils;

/**
 * 异常工具类
 *
 * @author zhoutaotao
 * @date 2019/5/15
 */
public class ExceptionUtil {

    /**
     * 将检查异常转换为非检查异常
     */
    public static RuntimeException unchecked(Exception e) {
        return new RuntimeException(e);
    }


}
