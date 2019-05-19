package com.domain.food.utils;

import com.domain.food.consts.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.geom.IllegalPathStateException;
import java.io.*;

/**
 * 输入输出工具类
 *
 * @author zhoutaotao
 * @date 2019/5/15
 */
public class IoUtil {

    private static final Logger log = LoggerFactory.getLogger(IoUtil.class);

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

    /**
     * 创建新文件
     */
    public static void createFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("创建文件失败");
            }
        }
    }

    /**
     * 删除文件
     */
    public static void delete(String path) {
        int counter = 1;
        int maxTry = 10;
        File file = new File(path);
        if (file.exists()) {
            while (!file.delete()) {
                log.error("删除文件 [" + path + "]失败, 第" + ++counter + "次重试。");
                if (counter > maxTry) {
                    throw new IllegalPathStateException("删除[" + path + "]重试多次后仍失败。");
                }
            }
        }
    }

    /**
     * 重命名文件
     */
    public static void rename(String oldFilepath, String newFilepath) {
        File oldFile = new File(oldFilepath);
        File newFile = new File(newFilepath);
        try {
            if (!oldFile.exists()) {
                throw new IllegalArgumentException("原文件必须存在");
            }
            if (!newFile.exists()) {
                File parentFile = newFile.getParentFile();
                if (!parentFile.exists()) {
                    newFile.mkdirs();
                }
                newFile.createNewFile();
            }
            oldFile.renameTo(newFile);
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * 关闭流
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
