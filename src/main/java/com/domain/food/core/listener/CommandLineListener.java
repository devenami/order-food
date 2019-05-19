package com.domain.food.core.listener;

import com.domain.food.utils.ObjectUtil;
import com.domain.food.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 命令行监听器
 *
 * @author feb13th
 * @since 2019/5/19 19:45
 */
public class CommandLineListener implements Runnable, DisposableBean, ApplicationContextAware, InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(CommandLineListener.class);

    private ExecutorService executor = Executors.newFixedThreadPool(1);

    private ApplicationContext applicationContext;

    private Map<String, List<ICommandLineProcessor>> commandRunner = new HashMap<>();

    @Override
    public void run() {
        // 命令行扫描器
        Scanner scanner = new Scanner(System.in);
        // 监听命令行参数
        while (true) {
            String command = scanner.nextLine();
            if (StringUtil.isBlank(command)) {
                continue;
            }

            // 选择可执行的处理器
            List<ICommandLineProcessor> runners = commandRunner.get(command);
            if (!ObjectUtil.isNull(runners)) {
                runners.forEach(runner -> runner.process(command));
            }
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        // 将当前对象放入线程池
        executor.execute(this);

        ObjectProvider<ICommandLineProcessor> beanProvider = applicationContext.getBeanProvider(ICommandLineProcessor.class);
        // 注册命令对应的处理器
        for (ICommandLineProcessor next : beanProvider) {
            String[] commands = next.supportCommands();
            Arrays.stream(commands).forEach(command -> {
                List<ICommandLineProcessor> runners = commandRunner.get(command);
                if (ObjectUtil.isNull(runners)) {
                    runners = new ArrayList<>();
                    commandRunner.put(command, runners);
                }
                runners.add(next);
                runners.sort(Comparator.comparingInt(Ordered::getOrder));
            });
        }
    }

    @Override
    public void destroy() throws Exception {
        // 直接将当前监听的线程池停止
        executor.shutdownNow();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
