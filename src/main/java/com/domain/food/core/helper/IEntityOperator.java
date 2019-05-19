package com.domain.food.core.helper;

import java.util.List;
import java.util.Map;

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
     * 根据 name -> value 的对应关系获取实体
     *
     * @param map name -> value
     * @return entity
     */
    List<E> get(Map<String, Object> map);

    /**
     * 根据 name -> value 的对应关系获取实体
     *
     * @param map name -> value
     * @return entity, 如果存在多个则抛出异常
     */
    E getOne(Map<String, Object> map);

    /**
     * 保存实体对象
     */
    void save(E entity);

    /**
     * 更新实体， 更新之前，需要用户先从库中查询实体，然后修改查询到的实体进行更新
     *
     * @param entity 从库中查询到的实体，并对该实体进行了修改操作
     */
    void update(E entity);

    /**
     * 删除实体, 删除之前需要用户先从库中查询实体
     *
     * @param entity 被删除的实体
     */
    void delete(E entity);

    /**
     * 删除实体, 删除之前需要用户先从库中查询实体
     *
     * @param entity          被删除的实体
     * @param throwsException true:当id不存在是抛出异常
     */
    void delete(E entity, boolean throwsException);

    /**
     * 根据 id 删除实体
     *
     * @param id 主键
     */
    void deleteById(K id);

    /**
     * 根据 id 删除实体
     *
     * @param id              主键
     * @param throwsException true:当id不存在是抛出异常
     */
    void deleteById(K id, boolean throwsException);
}
