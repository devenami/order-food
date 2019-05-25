package com.domain.food.dao;

import com.domain.food.domain.Order;

import java.time.LocalDate;
import java.util.List;

/**
 * 订单
 *
 * @author zhoutaotao
 * @date 2019/5/25
 */
public interface IOrderDao {

    /**
     * 新增订单
     *
     * @param order 订单信息
     */
    void save(Order order);

    /**
     * 根据id查询订单信息
     *
     * @param orderId id
     * @return 订单信息
     */
    Order findById(String orderId);

    /**
     * 根据id删除订单信息
     *
     * @param orderId id
     */
    void deleteById(String orderId);

    /**
     * 查询用户指定
     * @param userCode
     * @param localDate
     * @return
     */
    List<Order> findListByUserCodeAndLocalDate(String userCode, LocalDate localDate);
}
