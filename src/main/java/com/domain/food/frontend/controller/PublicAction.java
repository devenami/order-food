package com.domain.food.frontend.controller;

import com.domain.food.consts.ErrorCode;
import com.domain.food.core.AbstractController;
import com.domain.food.domain.Result;
import com.domain.food.frontend.service.IPublicService;
import com.domain.food.utils.ObjectUtil;
import com.domain.food.utils.SessionUtil;
import com.domain.food.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对外可访问的接口
 *
 * @author zhoutaotao
 * @date 2019/5/16
 */
@Api(tags = "未拦截接口")
@RestController
@RequestMapping(value = "/public", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PublicAction extends AbstractController {

    @Autowired
    private IPublicService publicService;

    @PostMapping("/login")
    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userCode", value = "用户编码", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)
    })
    public Result<UserVO> login(String userCode, String password) {
        notBlank(userCode, "用户编码");
        notBlank(password, "密码");

        return Result.success(publicService.login(userCode, password));
    }

    @GetMapping("/user")
    @ApiOperation("获取当前登录的用户信息")
    public Result<UserVO> getUser() {
        UserVO user = SessionUtil.getUser();
        if (ObjectUtil.isNull(user)) {
            ErrorCode.LOGIN_USER_NOT_LOGIN.shutdown();
        }
        return Result.success(user);
    }

    @GetMapping("/logout")
    @ApiOperation("退出登录")
    public Result logout() {
        SessionUtil.removeUser();
        return Result.success();
    }


}
