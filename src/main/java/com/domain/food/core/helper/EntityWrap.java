package com.domain.food.core.helper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 实体包装类
 *
 * @param <E> 实体类型
 * @author feb13th
 * @since 2019/5/16 22:13
 */
@Getter
@Setter
@EqualsAndHashCode
public class EntityWrap<E> {

    /**
     * 过期时间
     */
    private long expireTime;

    /**
     * 实体
     */
    private E entity;

    /**
     * 实体操作类型
     */
    private Type type;

    public enum Type {
        DEFAULT, ADD, UPDATE, DELETE
    }
}
