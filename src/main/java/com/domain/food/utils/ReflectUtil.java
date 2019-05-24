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
            if (field.getType().isPrimitive()) {
                return getFieldByPrimitive(field, object);
            }
            data = (T) field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 获取基本数据类型的属性值
     *
     * @param field
     * @param object
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    private static <T> T getFieldByPrimitive(Field field, Object object) throws IllegalAccessException {
        Class<?> type = field.getType();
        T data = null;
        if (type == Byte.TYPE) {
            data = (T) Byte.valueOf(field.getByte(object));
        }
        if (type == Character.TYPE) {
            data = (T) Character.valueOf(field.getChar(object));
        }
        if (type == Short.TYPE) {
            data = (T) Short.valueOf(field.getShort(object));
        }
        if (type == Integer.TYPE) {
            data = (T) Integer.valueOf(field.getInt(object));
        }
        if (type == Long.TYPE) {
            data = (T) Long.valueOf(field.getLong(object));
        }
        if (type == Double.TYPE) {
            data = (T) Double.valueOf(field.getDouble(object));
        }
        if (type == Float.TYPE) {
            data = (T) Float.valueOf(field.getFloat(object));
        }
        if (type == Boolean.TYPE) {
            data = (T) Boolean.valueOf(field.getBoolean(object));
        }
        return data;
    }

    /**
     * 设置属性值
     *
     * @param field  属性
     * @param object 被设置的属性的对象
     * @param value  属性值
     */
    public static void set(Field field, Object object, Object value) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }

        try {
            if (field.getType().isPrimitive()) {
                setFieldByPrimitive(field, object, value);
            } else {
                field.set(object, value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置基本数据类型的属性
     */
    private static void setFieldByPrimitive(Field field, Object object, Object value) throws IllegalAccessException {
        if (ObjectUtil.isNull(value)) {
            return;
        }
        String v = value.toString();
        Class<?> type = field.getType();
        if (type == Byte.TYPE) {
            field.setByte(object, Byte.valueOf(v));
        }
        if (type == Character.TYPE) {
            field.setChar(object, v.toCharArray()[0]);
        }
        if (type == Short.TYPE) {
            field.setShort(object, Short.valueOf(v));
        }
        if (type == Integer.TYPE) {
            field.setInt(object, Integer.valueOf(v));
        }
        if (type == Long.TYPE) {
            field.setLong(object, Long.valueOf(v));
        }
        if (type == Double.TYPE) {
            field.setDouble(object, Double.valueOf(v));
        }
        if (type == Float.TYPE) {
            field.setFloat(object, Float.valueOf(v));
        }
        if (type == Boolean.TYPE) {
            field.setBoolean(object, Boolean.valueOf(v));
        }
    }

}
