package com.domain.food.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * json字符串和对象之间互转
 *
 * @author zhoutaotao
 * @date 2019/5/15
 */
public class JsonUtil {

    /**
     * 把 java 对象转换为 json 字符串
     */
    public static <T> String toJson(T data) {
        return JSONObject.toJSONString(data);
    }

    /**
     * 将 json 字符串转换为 java 对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return JSONObject.parseObject(json, clazz);
    }

    /**
     * 将 json 字符串数组转换为 java 数组
     */
    public static <T> List<T> fromJsonArray(String json, Class<T> clazz) {
        return JSONObject.parseArray(json, clazz);
    }
}
