package com.domain.food.core;

/**
 * 抽象的 dao
 *
 * @param <K> 主键类型
 * @param <E> 实体类型
 * @author zhoutaotao
 * @date 2019/5/16
 */
public class AbstractBean<K, E> extends AbstractAnalyseBean<K, E> implements IEntityOperator<K, E> {

    @Override
    public E getById(K id) {
        return null;
    }


    @Override
    public void save(E entity) {

    }

}