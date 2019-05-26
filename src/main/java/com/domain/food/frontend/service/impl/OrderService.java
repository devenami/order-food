package com.domain.food.frontend.service.impl;

import com.domain.food.core.AbstractService;
import com.domain.food.dao.IOrderDao;
import com.domain.food.dao.IProductDao;
import com.domain.food.domain.Order;
import com.domain.food.domain.Product;
import com.domain.food.frontend.service.IOrderService;
import com.domain.food.utils.*;
import com.domain.food.vo.OrderVO;
import com.domain.food.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单服务实现
 *
 * @author zhoutaotao
 * @date 2019/5/25
 */
@Service
public class OrderService extends AbstractService implements IOrderService {

    @Autowired
    private IOrderDao orderDao;
    @Autowired
    private IProductDao productDao;

    @Override
    public OrderVO addOrder(UserVO userVO, String productId) {
        Product product = productDao.findById(productId);
        Condition.notNull(product, "商品不存在");

        Order order = new Order();
        order.setId(KeyUtil.uuid());
        order.setProductId(productId);
        order.setProductName(product.getName());
        order.setUserCode(userVO.getUserCode());
        order.setUsername(userVO.getUsername());
        order.setOrderDate(DateUtil.getTimestamp(LocalDate.now()));
        order.setSave(System.currentTimeMillis());

        orderDao.save(order);

        OrderVO orderVO = new OrderVO();
        BeanUtil.copy(order, orderVO);
        orderVO.setOrderId(order.getId());

        return orderVO;
    }

    @Override
    public void deleteOrder(UserVO userVO, String orderId) {
        Order order = orderDao.findById(orderId);
        Condition.notNull(order, "订单不存在");
        Condition.isTrue(order.getUserCode().equals(userVO.getUserCode()), "只能删除自己的订单");
        orderDao.deleteById(orderId);
    }

    @Override
    public List<OrderVO> getOrderList(UserVO userVO, Long day) {
        LocalDate localDate = ObjectUtil.isNull(day) ? null : day == 0 ? LocalDate.now() : DateUtil.parseLocalDate(day);
        List<Order> orderList = orderDao.findListByUserCodeAndLocalDate(userVO.getUserCode(), localDate);
        List<OrderVO> list = new ArrayList<>();
        orderList.forEach(order -> {
            OrderVO orderVO = new OrderVO();
            BeanUtil.copy(order, orderVO);
            orderVO.setOrderId(order.getId());
            list.add(orderVO);
        });

        return list;
    }

    @Override
    public List<UserVO> getOtherTodayOrder() {
        List<Order> orderList = orderDao.findListByUserCodeAndLocalDate(null, LocalDate.now());
        Map<UserVO, UserVO> map = new HashMap<>();

        orderList.forEach(order -> {
            UserVO userVO = new UserVO();
            userVO.setUserCode(order.getUserCode());
            userVO.setUsername(order.getUsername());
            UserVO userOrder = map.get(userVO);
            if (ObjectUtil.isNull(userOrder)) {
                userOrder = new UserVO();
                userOrder.setUserCode(order.getUserCode());
                userOrder.setUsername(order.getUsername());
                map.put(userVO, userOrder);
            }
            List<OrderVO> orders = userOrder.getOrders();
            if (ObjectUtil.isNull(orders)) {
                orders = new ArrayList<>();
                userOrder.setOrders(orders);
            }
            OrderVO orderVO = new OrderVO();
            BeanUtil.copy(order, orderVO);
            orderVO.setOrderId(order.getId());
            orders.add(orderVO);
        });

        return new ArrayList<>(map.values());
    }
}
