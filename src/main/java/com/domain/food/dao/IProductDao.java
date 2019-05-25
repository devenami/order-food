package com.domain.food.dao;

import com.domain.food.domain.Product;

import java.util.List;

/**
 * 商品dao
 *
 * @author zhoutaotao
 * @date 2019/5/25
 */
public interface IProductDao {

    /**
     * 保存商品信息
     *
     * @param product 商品信息
     */
    void save(Product product);

    /**
     * 根据id查找商品
     *
     * @param productId 商品id
     * @return 商品信息
     */
    Product findById(String productId);

    /**
     * 根据id删除商品
     *
     * @param id id
     */
    void deleteById(String id);

    /**
     * 查询订单列表
     *
     * @return 订单列表
     */
    List<Product> findAll();
}
