package com.domain.food.service;

import com.domain.food.domain.User;

/**
 * 用户服务类
 *
 * @author feb13th
 * @since 2019/5/16 21:37
 */
public interface IUserService {

    /**
     * 新增用户
     */
    User addUser(String userCode, String username, int sex, String department);

    /**
     * 更新用户信息
     */
    User updateUser(String userCode, String username, Integer sex, String department);

    /**
     * 删除用户信息
     */
    User deleteUser(String userCode);
}
