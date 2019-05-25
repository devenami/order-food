package com.domain.food.frontend.service;

import com.domain.food.vo.OrderVO;
import com.domain.food.vo.UserVO;

import java.util.List;

/**
 * 订单服务
 *
 * @author zhoutaotao
 * @date 2019/5/25
 */
public interface IOrderService {

    /**
     * 新增订单
     *
     * @param userVO    用户信息
     * @param productId 商品id
     * @return 订单信息
     */
    OrderVO addOrder(UserVO userVO, String productId);

    /**
     * 删除订单
     *
     * @param userVO  用户信息
     * @param orderId 订单id
     */
    void deleteOrder(UserVO userVO, String orderId);

    /**
     * 查询订单列表
     *
     * @param userVO 用户信息
     * @param day    指定时间
     * @return 订单列表
     */
    List<OrderVO> getOrderList(UserVO userVO, Long day);
}
