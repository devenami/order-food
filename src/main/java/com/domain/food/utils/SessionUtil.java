package com.domain.food.utils;

import com.domain.food.consts.Constant;
import com.domain.food.vo.UserVO;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话管理
 *
 * @author zhoutaotao
 * @date 2019/5/24
 */
public class SessionUtil {

    /**
     * 用户sessionId -> userVO
     */
    private static final Map<String, UserVO> userCache = new ConcurrentHashMap<>();

    /**
     * 添加用户
     *
     * @param userVO 用户信息
     */
    public static void addUser(UserVO userVO) {
        // 存储用户信息
        HttpServletRequest request = HttpUtil.getHttpServletRequest();
        HttpSession session = request.getSession(true);
        String sessionId = session.getId();
        userCache.put(sessionId, userVO);
        // 添加cookie
        Cookie cookie = new Cookie(Constant.USER_COOKIE_NAME, sessionId);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(Constant.USER_COOKIE_EXPIRE_TIME);
        HttpServletResponse response = HttpUtil.getHttpServletResponse();
        response.addCookie(cookie);
    }

    /**
     * 删除用户信息
     */
    public static void removeUser() {
        String sessionId = getSessionId(HttpUtil.getHttpServletRequest());
        if (StringUtil.isBlank(sessionId)) {
            return;
        }
        userCache.remove(sessionId);
        Cookie cookie = new Cookie(Constant.USER_COOKIE_NAME, sessionId);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(Constant.USER_COOKIE_EXPIRE_TIME_NONE);
        HttpServletResponse response = HttpUtil.getHttpServletResponse();
        response.addCookie(cookie);
    }

    /**
     * 获取用户
     *
     * @return 用户信息
     */
    public static UserVO getUser() {
        String sessionId = getSessionId(HttpUtil.getHttpServletRequest());
        if (StringUtil.isBlank(sessionId)) {
            return null;
        }
        return userCache.get(sessionId);
    }

    /**
     * 获取sessionId
     *
     * @return sessionId
     */
    private static String getSessionId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (Constant.USER_COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
