package com.domain.food.dao.core;

import com.domain.food.utils.ObjectUtil;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽象的 dao
 *
 * @param K     主键类型
 * @param E 实体类型
 * @author zhoutaotao
 * @date 2019/5/16
 */
public class AbstractDao<K, E> extends AbstractAnalyseDao<K, E> implements IEntityOperator<K, E> {

    /**
     * 根据id获取当天的数据
     */
    @Override
    public E getById(K id) {
        return getById(LocalDate.now(), id);
    }

    /**
     * 根据id查询指定日期数据
     */
    @Override
    public E getById(LocalDate localDate, K id) {
        if (getEntityHolder().isIdExists()) {
            Map<K, E> map = cacheWithId.get(localDate);
            if (ObjectUtil.isNull(map)) {
                loadDataFromDisk(localDate);
            }
            if (ObjectUtil.isNull(map)) {
                map = new ConcurrentHashMap<>();
                cacheWithId.put(localDate, map);
            }
            return map.get(id);
        }
        throw new IllegalArgumentException("实体不存在id");
    }


}
