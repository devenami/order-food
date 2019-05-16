package com.domain.food.controller;

import com.domain.food.domain.Result;
import com.domain.food.domain.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对外可访问的接口
 *
 * @author zhoutaotao
 * @date 2019/5/16
 */
@RestController
@RequestMapping(value = "/food/public", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PublicAction {


    public Result<User> login(String userCode, String password) {
        
        return null;
    }

}
