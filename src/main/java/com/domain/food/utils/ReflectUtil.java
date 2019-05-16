package com.domain.food.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 反射工具类
 *
 * @author feb13th
 * @since 2019/5/16 21:10
 */
public class ReflectUtil {

    @SuppressWarnings("unchecked")
    public static <T> T get(Field field, Object object, Class<T> clazz) {
        T data = null;
        try {
            Object obj = field.get(object);
            if (obj instanceof Class) {
                data = (T) obj;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 获取泛型数组
     */
    public static List<? extends Class<? extends Type>> getGenerics(Class<?> clazz) {
        List<? extends Class<? extends Type>> list = new ArrayList<>();
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) genericSuperclass;
            Type[] actualTypeArguments = type.getActualTypeArguments();
            list = Arrays
                    .stream(actualTypeArguments)
                    .map(Type::getClass)
                    .collect(Collectors.toList());
        }
        return list;
    }
}
