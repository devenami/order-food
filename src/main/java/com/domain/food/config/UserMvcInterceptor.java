package com.domain.food.config;

import com.domain.food.consts.ErrorCode;
import com.domain.food.utils.ObjectUtil;
import com.domain.food.utils.SessionUtil;
import com.domain.food.vo.UserVO;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @author feb13th
 * @since 2019/5/26 16:20
 */
public class UserMvcInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserVO user = SessionUtil.getUser();
        if (ObjectUtil.isNull(user)) {
            ErrorCode.LOGIN_USER_NOT_LOGIN.shutdown();
        }
        return true;
    }
}
