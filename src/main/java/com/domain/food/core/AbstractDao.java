package com.domain.food.core;

import com.domain.food.core.helper.AbstractContainer;
import com.domain.food.core.helper.EntityWrap;
import com.domain.food.core.helper.IEntityOperator;
import com.domain.food.utils.ObjectUtil;

/**
 * 抽象的 helper
 *
 * @param <K> 主键类型
 * @param <E> 实体类型
 * @author zhoutaotao
 * @date 2019/5/16
 */
public class AbstractDao<K, E> extends AbstractContainer<K, E> implements IEntityOperator<K, E> {

    @Override
    public E getById(K id) {
        EntityWrap<E> entityWrap = getEntityCacheMap().get(id);
        if (ObjectUtil.isNull(entityWrap)) {
            loadDataFromDisk();
            entityWrap = getEntityCacheMap().get(id);
        }
        return ObjectUtil.isNull(entityWrap) ? null : entityWrap.getEntity();
    }


    @Override
    public void save(E entity) {
        K id = getIdValue(entity);
        E exists = getById(id);
        if (!ObjectUtil.isNull(exists)) {
            throw new IllegalArgumentException("主键已存在, id = " + id);
        }
        EntityWrap<E> entityWrap = new EntityWrap<>();
        entityWrap.setEntity(entity);
        entityWrap.setExpireTime(getExpireTime());
        entityWrap.setType(EntityWrap.Type.ADD);
        getEntityCacheMap().put(id, entityWrap);
    }

}