package com.domain.food.service.impl;

import com.domain.food.domain.User;
import com.domain.food.service.IPublicService;
import org.springframework.stereotype.Service;

/**
 * 公开服务是吸纳类
 *
 * @author zhoutaotao
 * @date 2019/5/16
 */
@Service
public class PublicService implements IPublicService {

    @Override
    public User login(String userCode, String password) {

        return null;
    }
}
