package com.domain.food.utils;

import com.domain.food.consts.Constant;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 输入输出工具类
 *
 * @author zhoutaotao
 * @date 2019/5/15
 */
public class IoUtil {

    /**
     * 从文件中读取字符串
     *
     * @param file 文件名
     */
    public static String readString(String file) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), Constant.DEFAULT_CHARSET))) {
            String tmp;
            while ((tmp = br.readLine()) != null) {
                result.append(tmp);
            }
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
        return result.toString();
    }

}
