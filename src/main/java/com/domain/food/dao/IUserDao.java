package com.domain.food.dao;

import com.domain.food.domain.User;

/**
 * 用户数据持久化接口
 */
public interface IUserDao {

    /**
     * 新增用户
     */
    void saveUser(User user);

    /**
     * 更新用户信息
     */
    void updateUser(User user);

    /**
     * 根据用户编码获取用户信息
     */
    User getByUserCode(String userCode);

    /**
     * 根据用户编码删除用户信息
     */
    void deleteUser(String userCode);
}
