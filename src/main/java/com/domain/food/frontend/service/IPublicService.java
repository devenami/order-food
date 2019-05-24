package com.domain.food.frontend.service;

import com.domain.food.vo.UserVO;

/**
 * 公共服务接口
 */
public interface IPublicService {

    UserVO login(String userCode, String password);

}
