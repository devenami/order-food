package com.domain.food.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * IO测试类
 */
public class IoUtilTest {

    @Test
    public void testLocalPath() {
        String file = "a.jpg";
        String classpath = "classpath:/file/path";
        System.out.println(IoUtil.localPath(classpath, file));

        String filepath = "file:d:/file/path";
        System.out.println(IoUtil.localPath(filepath, file));
    }

    @Test
    public void testCreateFile() {
        String path = "D:/test/file/1.json";
        IoUtil.createFile(path);
    }

}