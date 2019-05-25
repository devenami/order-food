package com.domain.food.core;

import com.domain.food.core.helper.MoreThanOneElementException;
import com.domain.food.core.helper.AbstractContainer;
import com.domain.food.core.helper.EntityWrap;
import com.domain.food.core.helper.IEntityOperator;
import com.domain.food.utils.JsonUtil;
import com.domain.food.utils.ObjectUtil;
import com.domain.food.utils.ReflectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽象的 dao
 *
 * @param <K> 主键类型
 * @param <E> 实体类型
 * @author zhoutaotao
 * @date 2019/5/16
 */
public class AbstractDao<K, E> extends AbstractContainer<K, E> implements IEntityOperator<K, E> {

    @Override
    public E findById(K id) {
        Map<String, Object> map = new HashMap<>();
        map.put(getEntityHolder().getIdField().getName(), id);
        return findOne(map);
    }

    @Override
    public List<E> findAll() {
        return find(new HashMap<>());
    }

    @Override
    public List<E> find(Map<String, Object> map) {
        final List<E> list = new ArrayList<>();
        loadData(map);
        getEntityCacheMap().forEach((id, entityWrap) -> {
            // 禁止对已删除的数据进行操作
            EntityWrap.Type type = entityWrap.getType();
            if (type == EntityWrap.Type.DELETE) {
                return;
            }

            E entity = entityWrap.getEntity();
            // 过滤出所有参数name即参数值匹配的数据
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String name = entry.getKey();
                Object value = entry.getValue();
                Field field = getEntityHolder().getField(name);
                Object realValue = ReflectUtil.get(field, entity);
                if (!value.equals(realValue)) {
                    return;
                }
            }
            list.add(entity);
        });
        return list;
    }

    @Override
    public E findOne(Map<String, Object> map) {
        List<E> list = find(map);
        if (list.size() > 1) {
            throw new MoreThanOneElementException(JsonUtil.toJson(list));
        }
        return list.size() == 1 ? list.get(0) : null;
    }

    @Override
    public void save(E entity) {
        K id = getIdValue(entity);
        E exists = findById(id);
        if (!ObjectUtil.isNull(exists)) {
            throw new IllegalArgumentException("主键已存在, id = " + id);
        }
        EntityWrap<E> entityWrap = new EntityWrap<>();
        entityWrap.setEntity(entity);
        entityWrap.setExpireTime(getExpireTime());
        entityWrap.setType(EntityWrap.Type.ADD);
        getEntityCacheMap().put(id, entityWrap);
    }

    @Override
    public void update(E entity) {
        K id = getIdValue(entity);
        E exists = findById(id);
        if (ObjectUtil.isNull(exists)) {
            throw new IllegalArgumentException("实体不存在, id = " + id);
        }
        EntityWrap<E> entityWrap = getEntityCacheMap().get(id);
        entityWrap.setEntity(entity);
        entityWrap.setType(EntityWrap.Type.UPDATE);
        entityWrap.setExpireTime(getExpireTime());
    }

    @Override
    public void delete(E entity) {
        delete(entity, false);
    }

    @Override
    public void delete(E entity, boolean throwsException) {
        K id = getIdValue(entity);
        deleteById(id, throwsException);
    }

    @Override
    public void deleteById(K id) {
        deleteById(id, false);
    }

    @Override
    public void deleteById(K id, boolean throwsException) {
        E exists = findById(id);
        if (ObjectUtil.isNull(exists) && throwsException) {
            throw new IllegalArgumentException("实体不存在, id = " + id);
        } else {
            EntityWrap<E> entityWrap = getEntityCacheMap().get(id);
            entityWrap.setType(EntityWrap.Type.DELETE);
        }
    }

}