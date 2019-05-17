package com.domain.food.utils;

import java.lang.reflect.Field;

/**
 * 反射工具类
 *
 * @author feb13th
 * @since 2019/5/16 21:10
 */
public class ReflectUtil {

    /**
     * 获取属性值
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(Field field, Object object) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        T data = null;
        try {
            data = (T) field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return data;
    }

}
