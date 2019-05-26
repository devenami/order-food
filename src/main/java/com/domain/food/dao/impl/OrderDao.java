package com.domain.food.dao.impl;

import com.domain.food.core.AbstractDao;
import com.domain.food.dao.IOrderDao;
import com.domain.food.domain.Order;
import com.domain.food.utils.DateUtil;
import com.domain.food.utils.ObjectUtil;
import com.domain.food.utils.StringUtil;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单dao
 *
 * @author zhoutaotao
 * @date 2019/5/25
 */
@Repository
public class OrderDao extends AbstractDao<String, Order> implements IOrderDao {

    @Override
    public List<Order> findListByUserCodeAndLocalDate(String userCode, LocalDate localDate) {
        Map<String, Object> map = new HashMap<>();
        if (!StringUtil.isBlank(userCode)) {
            map.put("userCode", userCode);
        }
        if (!ObjectUtil.isNull(localDate)) {
            map.put("orderDate", DateUtil.getTimestamp(localDate));
        }
        return super.find(map);
    }
}
