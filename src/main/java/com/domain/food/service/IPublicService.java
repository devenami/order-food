package com.domain.food.service;

import com.domain.food.domain.User;

/**
 * 公共服务接口
 */
public interface IPublicService {

    User login(String userCode, String password);

}
