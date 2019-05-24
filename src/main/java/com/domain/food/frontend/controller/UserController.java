package com.domain.food.frontend.controller;

import com.domain.food.core.AbstractController;
import com.domain.food.domain.Result;
import com.domain.food.frontend.service.IUserService;
import com.domain.food.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping(value = "/user")
public class UserController extends AbstractController {

    @Autowired
    private IUserService userService;

    @PostMapping("/add")
    public Result<UserVO> addUser(String userCode, String password, String username, Integer sex, String department) {
        notBlank(userCode, "userCode");
        notBlank(password, "password");
        notBlank(username, "username");
        greatEqual0(sex, "sex");
        notBlank(department, "department");

        return Result.success(userService.addUser(userCode, password, username, sex, department));
    }

    @PostMapping("/update")
    public Result<UserVO> updateUser(String userCode, String password, String username, Integer sex, String department) {
        notBlank(userCode, "userCode");

        return Result.success(userService.updateUser(userCode, password, username, sex, department));
    }

    @GetMapping("/delete")
    public Result<UserVO> deleteUser(String userCode) {
        notBlank(userCode, "userCode");
        return Result.success(userService.deleteUser(userCode));
    }
}
