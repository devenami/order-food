package com.domain.food.dao.core;

import java.time.LocalDate;

/**
 * 实体操作
 *
 * @param K 主键
 * @param E 实体
 * @author zhoutaotao
 */
public interface IEntityOperator<K, E> {

    /**
     * 根据id获取当天的数据
     */
    public E getById(K id);

    /**
     * 根据id查询指定日期数据
     */
    public E getById(LocalDate localDate, K id);
}
