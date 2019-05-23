package com.domain.food.controller;

import com.domain.food.core.AbstractController;
import com.domain.food.domain.Result;
import com.domain.food.domain.User;
import com.domain.food.service.IPublicService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/public", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PublicAction extends AbstractController {

    @Autowired
    private IPublicService publicService;

    public Result<User> login(String userCode, String password) {
        notBlank(userCode, "用户编码");
        notBlank(password, "密码");

        return Result.success(publicService.login(userCode, password));
    }

}
