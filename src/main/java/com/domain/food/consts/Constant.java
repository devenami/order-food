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

    public static final String DEFAULT_TEXT_ERROR = "error";

    public static final String DEFAULT_TEXT_UNKNOWN = "known";

    public static final String DEFAULT_LINE_CENTER = "-";

    public static final String DEFAULT_SUFFIX_JSON = ".json";

    /**
     * 命令行处理器执行顺序开始值
     */
    public static final int COMMAND_LINE_PROCESSOR_ORDER_MIN = Ordered.LOWEST_PRECEDENCE - 1000;
}
