package com.domain.food.core.listener;

import com.domain.food.consts.Constant;

/**
 * 命令行监听执行器顺序
 *
 * @author feb13th
 * @since 2019/5/19 21:01
 */
public enum CommandLineProcessorOrder {

    /**
     * 数据持久化
     */
    DaoCommandProcessor(Constant.COMMAND_LINE_PROCESSOR_ORDER_MIN + 100),
    /**
     * 程序退出命令
     */
    ApplicationExitCommandProcessor(Constant.COMMAND_LINE_PROCESSOR_ORDER_MIN + 999),
    ;

    /**
     * 顺序越少，越先执行
     */
    private int order;

    CommandLineProcessorOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
