package com.domain.food.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具
 *
 * @author zhoutaotao
 * @date 2019/5/23
 */
public class DigestUtil {

    public static String md5(String source) {
        Condition.notBlank(source, "被加密的数据不能为null");
        try {
            MessageDigest instance = MessageDigest.getInstance("md5");
            byte[] digest = instance.digest(source.getBytes());
            return ByteUtil.byte2Hex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }


}
