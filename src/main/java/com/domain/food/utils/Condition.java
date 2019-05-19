package com.domain.food.utils;

import com.domain.food.config.BusinessException;
import com.domain.food.consts.ErrorCode;

/**
 * 条件检查工具
 *
 * @author feb13th
 * @since 2019/5/19 18:49
 */
public class Condition {

    /**
     * 检查字符串不能为 null 和 ""
     */
    public static void notBlank(String str, String ext) {
        if (StringUtil.isBlank(str)) {
            throw new BusinessException(ErrorCode.CONDITION_CHECK, ext);
        }
    }

    /**
     * 检查参数不能为 null
     */
    public static <T> void notNull(T obj, String ext) {
        if (ObjectUtil.isNull(obj)) {
            throw new BusinessException(ErrorCode.CONDITION_CHECK, ext);
        }
    }

}
