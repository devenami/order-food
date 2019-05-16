package com.domain.food.dao;

import com.domain.food.domain.User;

/**
 * 用户数据持久化接口
 */
public interface IUserDao {

    void save(User user);

}
