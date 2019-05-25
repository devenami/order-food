package com.domain.food.utils;

import java.util.UUID;

/**
 * 唯一键生成工具
 *
 * @author zhoutaotao
 * @date 2019/5/25
 */
public class KeyUtil {

    /**
     * 获取UUID
     *
     * @return uuid
     */
    public static String uuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }

}
