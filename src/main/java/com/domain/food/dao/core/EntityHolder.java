package com.domain.food.dao.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 实体属性容器
 *
 * @param K 主键类型
 * @param E 实体类型
 * @author zhoutaotao
 * @date 2019/5/16
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class EntityHolder<K, E> {

    private boolean idExists;

    private Field idField;

    private Class<K> idClazz;

    private Class<E> entityClazz;

    private Entity entityAnnotation;

    private Id idAnnotation;

    private Map<String, Field> fieldMap = new ConcurrentHashMap<>();

    /**
     * 添加属性名和属性的对应关系
     */
    public void addField(String name, Field field) {
        fieldMap.put(name, field);
    }

    /**
     * 根据属性名获取属性
     */
    public Field getField(String name) {
        return fieldMap.get(name);
    }
}
