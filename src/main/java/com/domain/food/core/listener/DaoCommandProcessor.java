package com.domain.food.core.listener;

import com.domain.food.core.helper.IDaoClear;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashSet;
import java.util.Set;

/**
 * 数据源获取命令行的处理
 *
 * @author feb13th
 * @since 2019/5/19 20:27
 */
public class DaoCommandProcessor implements ICommandLineProcessor, ApplicationContextAware {

    private static final String COMMAND_CLEAR = "clear";
    private static final String COMMAND_CLOSE = "close";

    private ApplicationContext applicationContext;

    private Set<IDaoClear> daoRefreshers = new HashSet<>();

    @Override
    public String[] supportCommands() {
        return new String[]{COMMAND_CLEAR, COMMAND_CLOSE};
    }

    @Override
    public void process(String command) {
        daoRefreshers.forEach(refresher -> {
            try {
                if (command.equals(COMMAND_CLEAR)) {
                    refresher.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ObjectProvider<IDaoClear> beanProvider = applicationContext.getBeanProvider(IDaoClear.class);
        beanProvider.forEach(daoRefreshers::add);
    }

    @Override
    public int getOrder() {
        return CommandLineProcessorOrder.DaoCommandProcessor.getOrder();
    }
}
