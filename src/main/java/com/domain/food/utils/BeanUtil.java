package com.domain.food.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对象工具类
 *
 * @author zhoutaotao
 * @date 2019/5/24
 */
public class BeanUtil {

    /**
     * 将源对象内的属性值拷贝到目标对象内
     *
     * @param source  源对象
     * @param target  目标对象
     * @param ignores 忽略的属性集合
     * @param <S>     源对象类型
     * @param <T>     目标对象类型
     */
    public static <S, T> void copy(S source, T target, String... ignores) {
        // 存放属性名和属性
        Map<String, Field> targetMap = new HashMap<>();
        // 忽略的属性
        List<String> ignoreFields = Arrays.asList(ignores);
        Field[] targetFields = target.getClass().getDeclaredFields();
        Arrays.stream(targetFields).forEach(field -> {
            String name = field.getName();
            if (!ignoreFields.contains(name)) {
                targetMap.put(field.getName(), field);
            }
        });

        // 设置对应的属性
        targetMap.keySet().forEach(name -> {
            try {
                Field sourceField = source.getClass().getDeclaredField(name);
                Object sourceValue = ReflectUtil.get(sourceField, source);
                Field targetField = targetMap.get(name);
                ReflectUtil.set(targetField, target, sourceValue);
            } catch (NoSuchFieldException ignore) {
            }
        });
    }

}
