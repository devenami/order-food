package com.domain.food.core.listener;

import org.springframework.core.Ordered;

/**
 * 命令行监听器
 *
 * @author feb13th
 * @since 2019/5/19 19:59
 */
public interface ICommandLineProcessor extends Ordered {

    /**
     * 支持的命令集合
     */
    String[] supportCommands();

    /**
     * 命令处理方法
     *
     * @param command 当前接受到的命令
     */
    void process(String command);
}
