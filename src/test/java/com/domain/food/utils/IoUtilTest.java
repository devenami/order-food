package com.domain.food.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * IO测试类
 */
public class IoUtilTest {

    @Test
    public void testCreateFile() {
        String path = "D:/test/file/1.json";
        IoUtil.createFile(path);
    }

}