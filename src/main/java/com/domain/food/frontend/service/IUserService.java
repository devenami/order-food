package com.domain.food.frontend.service;

import com.domain.food.vo.UserVO;

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
    UserVO addUser(String userCode, String password, String username, int sex, String department);

    /**
     * 更新用户信息
     */
    UserVO updateUser(String userCode, String password, String username, Integer sex, String department);

    /**
     * 删除用户信息
     */
    UserVO deleteUser(String userCode);
}
