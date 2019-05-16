package com.domain.food.core;

import com.domain.food.utils.ObjectUtil;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 抽象的实体分析类
 *
 * @param <K> 主键类型
 * @param <E> 实体类型
 * @author zhoutaotao
 * @date 2019/5/16
 */
public abstract class AbstractAnalyseBean<K, E> extends AbstractContainer<K, E> {

    // id 和 实体的数据类型
    private EntityHolder<K, E> entityHolder = new EntityHolder<>();

    /**
     * 分析实体
     */
    @Override
    protected synchronized void analyseEntity() {
        Class<E> entityClass = getEntityClass();
        // 检查数据持久化名称
        Entity entityAnnotation = entityClass.getDeclaredAnnotation(Entity.class);
        Assert.notNull(entityAnnotation, "数据实体必须使用 [" + Entity.class.getName() + "]");
        String persistFileName = entityAnnotation.name();
        Assert.isTrue(!existsFileName.containsKey(persistFileName), "存在相同的实体名 [" + persistFileName + "]");
        existsFileName.put(persistFileName, EMPTY_OBJECT);
        log.debug("获取到 [" + entityClass.getName() + "] 中配置的文件名[" + persistFileName + "]");

        getEntityHolder().setEntityAnnotation(entityAnnotation);

        // 处理主键
        Field[] declaredFields = entityClass.getDeclaredFields();
        Assert.notEmpty(declaredFields, "实体内部必须存在属性");
        log.debug("[" + entityClass.getName() + "]中存在的属性数量[" + declaredFields.length + "]");

        Id idAnnotation = null;
        Field idField = null;
        for (Field field : declaredFields) {
            Id fieldId = field.getDeclaredAnnotation(Id.class);
            if (fieldId != null && idAnnotation != null) {
                throw new IllegalStateException("实体 [" + entityClass.getName() + "] 存在多个 [" + Id.class.getName() + "]");
            } else if (fieldId != null && idAnnotation == null) {
                idAnnotation = fieldId;
                idField = field;
            }
            // 存储所有的属性
            getEntityHolder().addField(field.getName(), field);
        }

        Assert.isTrue(!ObjectUtil.isNull(idAnnotation) && !ObjectUtil.isNull(idField),
                "[" + entityClass.getName() + "] 中缺少Id");

        Class<K> idClass = getIdClass();
        Assert.isTrue(idClass == idField.getType(),
                getClass().getName() + "中声明的id类型为: [" + idClass.getName()
                        + "], 而实体[" + entityClass.getName() + "]中的id类型为: [" + idField.getType().getName() + "]");
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<K> getIdClass() {
        if (!ObjectUtil.isNull(getEntityHolder().getIdClazz())) {
            return getEntityHolder().getIdClazz();
        }
        Class<K> id = null;
        Lock lock = new ReentrantLock();
        try {
            lock.lock();
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type idClazz = parameterizedType.getActualTypeArguments()[0];
                if (idClazz instanceof Class) {
                    id = (Class<K>) idClazz;
                }
            }
        } finally {
            lock.unlock();
        }

        log.debug("获取[" + getClass().getName() + "]中声明的id类型: [" + id.getName() + "]");

        Assert.notNull(id, getClass().getName() + " 泛型实体不能为空");
        getEntityHolder().setIdClazz(id);
        return id;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<E> getEntityClass() {
        if (!ObjectUtil.isNull(getEntityHolder().getEntityClazz())) {
            return getEntityHolder().getEntityClazz();
        }
        Class<E> entity = null;
        Lock lock = new ReentrantLock();
        try {
            lock.lock();
            // 实体信息
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type clazz = parameterizedType.getActualTypeArguments()[1];
                if (clazz instanceof Class) {
                    entity = (Class<E>) clazz;
                }
            }
        } finally {
            lock.unlock();
        }
        log.debug("获取[" + getClass().getName() + "]中声明的实体类型: [" + entity.getName() + "]");

        Assert.notNull(entity, getClass().getName() + " 泛型实体不能为空");
        getEntityHolder().setEntityClazz(entity);
        return entity;
    }

    /**
     * 获取实体管理器
     */
    @Override
    public EntityHolder<K, E> getEntityHolder() {
        return entityHolder;
    }


}
