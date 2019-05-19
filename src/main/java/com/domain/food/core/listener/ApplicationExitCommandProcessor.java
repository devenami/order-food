package com.domain.food.core.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据源获取命令行的处理
 *
 * @author feb13th
 * @since 2019/5/19 20:27
 */
public class ApplicationExitCommandProcessor implements ICommandLineProcessor {

    private static final Logger log = LoggerFactory.getLogger(ApplicationExitCommandProcessor.class);

    private static final String COMMAND_CLOSE = "close";

    @Override
    public String[] supportCommands() {
        return new String[]{COMMAND_CLOSE};
    }

    @Override
    public void process(String command) {
        if (command.equals(COMMAND_CLOSE)) {
            log.error("接收到命令行传递的程序退出命令: [" + command + "]");
            System.exit(0);
        }
    }

    @Override
    public int getOrder() {
        return CommandLineProcessorOrder.DaoCommandProcessor.getOrder();
    }
}
