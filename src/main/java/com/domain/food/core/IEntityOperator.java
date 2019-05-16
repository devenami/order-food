package com.domain.food.core;

/**
 * 实体操作
 *
 * @param <K> 主键
 * @param <E> 实体
 * @author zhoutaotao
 */
public interface IEntityOperator<K, E> {

    /**
     * 根据id获取数据
     */
    E getById(K id);


    /**
     * 保存实体对象
     */
    void save(E entity);

}
