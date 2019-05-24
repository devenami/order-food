package com.domain.food.consts;

import org.springframework.core.Ordered;

/**
 * 常量
 *
 * @author zhoutaotao
 * @date 2019/5/15
 */
public class Constant {

    public static final String DEFAULT_CHARSET = "UTF-8";

    public static final String DEFAULT_TEXT_ERROR = "shutdown";

    public static final String DEFAULT_TEXT_UNKNOWN = "unknown";

    public static final String DEFAULT_LINE_CENTER = "-";

    public static final String DEFAULT_SUFFIX_JSON = ".json";

    //命令行处理器执行顺序开始值
    public static final int COMMAND_LINE_PROCESSOR_ORDER_MIN = Ordered.LOWEST_PRECEDENCE - 1000;

    // 用户cookie名称
    public static final String USER_COOKIE_NAME = "__user__";
    // cookie 过期时间
    public static final int USER_COOKIE_EXPIRE_TIME = 1000 * 60 * 60 * 24;
    // cookie 立即过期时间
    public static final int USER_COOKIE_EXPIRE_TIME_NONE = 0;
}
