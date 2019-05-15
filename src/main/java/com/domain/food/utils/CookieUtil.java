package com.domain.food.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理各种Cookie信息
 *
 * @author zhoutaotao
 * @date 2019/5/15
 */
public class CookieUtil {

    public static String getCookie(String name) {
        Cookie[] cookies = HttpUtil.getHttpServletRequest().getCookies();
        if (ObjectUtil.isNull(cookies)) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public static void addCookie(String name, String value) {
        HttpServletResponse response = HttpUtil.getHttpServletResponse();
        Cookie cookie = new Cookie(name, value);
        response.addCookie(cookie);
    }

}
