package com.domain.food.frontend.controller;

import com.domain.food.core.AbstractController;
import com.domain.food.domain.Result;
import com.domain.food.frontend.service.IUserService;
import com.domain.food.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "用户管理")
@RestController
@RequestMapping(value = "/user")
public class UserController extends AbstractController {

    @Autowired
    private IUserService userService;

    @PostMapping("/add")
    @ApiOperation("新增用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userCode", value = "用户编码", required = true),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true),
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "sex", value = "性别", required = true, allowableValues = "0,1"),
            @ApiImplicitParam(name = "department", value = "部门", required = true)
    })
    public Result<UserVO> addUser(String userCode, String password, String username, Integer sex, String department) {
        notBlank(userCode, "userCode");
        notBlank(password, "password");
        notBlank(username, "username");
        greatEqual0(sex, "sex");
        notBlank(department, "department");

        return Result.success(userService.addUser(userCode, password, username, sex, department));
    }

    @PostMapping("/update")
    @ApiOperation("更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userCode", value = "用户编码", required = true),
            @ApiImplicitParam(name = "password", value = "用户密码"),
            @ApiImplicitParam(name = "username", value = "用户名"),
            @ApiImplicitParam(name = "sex", value = "性别", allowableValues = "0,1"),
            @ApiImplicitParam(name = "department", value = "部门")
    })
    public Result<UserVO> updateUser(String userCode, String password, String username, Integer sex, String department) {
        notBlank(userCode, "userCode");

        return Result.success(userService.updateUser(userCode, password, username, sex, department));
    }

    @GetMapping("/delete")
    @ApiOperation("删除用户")
    @ApiImplicitParam(name = "userCode", value = "用户编码", required = true)
    public Result<UserVO> deleteUser(String userCode) {
        notBlank(userCode, "userCode");
        return Result.success(userService.deleteUser(userCode));
    }
}
