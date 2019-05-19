package com.domain.food.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 集合工具类
 *
 * @author feb13th
 * @since 2019/5/19 17:24
 */
public class CollectionUtil {

    /**
     * 拷贝 map
     */
    public static <K, V> Map<K, V> copy(Map<K, V> map) {
        Map<K, V> newMap = new HashMap<>();
        if (ObjectUtil.isNull(map)) {
            return newMap;
        }
        map.forEach(newMap::put);
        return newMap;
    }

}
