package com.domain.food.controller;

import com.domain.food.core.AbstractController;
import com.domain.food.domain.Result;
import com.domain.food.domain.User;
import com.domain.food.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理
 *
 * @author feb13th
 * @since 2019/5/16 21:35
 */
@RestController
@RequestMapping(value = "/food/user")
public class UserController extends AbstractController {

    @Autowired
    private IUserService userService;

    @PostMapping("/add")
    public Result<User> addUser(String username, int sex, String department) {
        notBlank(username, "username");
        positiveNumber(sex, "sex");
        notBlank(department, "department");

        return Result.success(userService.addUser(username, sex, department));
    }

}
