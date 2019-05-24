package com.domain.food.core.helper;

/**
 * 数据多于一个抛出该异常
 *
 * @author feb13th
 * @since 2019/5/19 18:14
 */
public class MoreThanOneElementException extends RuntimeException {

    public MoreThanOneElementException() {
        super();
    }

    public MoreThanOneElementException(String message) {
        super(message);
    }
}
