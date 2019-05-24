package com.domain.food;

import com.domain.food.config.ConfigProperties;
import com.domain.food.core.listener.ApplicationExitCommandProcessor;
import com.domain.food.core.listener.CommandLineListener;
import com.domain.food.core.listener.DaoCommandProcessor;
import com.domain.food.core.listener.ICommandLineProcessor;
import com.domain.food.vo.UserVO;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
public class Application implements ApplicationContextAware {

    private ApplicationContext applicationContext;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Docket swaggerConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(UserVO.class)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.domain.food.frontend"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("轻量化点餐系统")
                .contact(new Contact("feb13th", "https://github.com/feb13th", "zttmax@126.com"))
                .description("极致轻量化")
                .version("1.0.0")
                .build();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 创建命令行监听器
     */
    @Bean
    public CommandLineListener commandLineListener() {
        CommandLineListener listener = new CommandLineListener();
        listener.setApplicationContext(applicationContext);
        return listener;
    }

    /**
     * 创建dao命令行处理器
     */
    @Bean
    public ICommandLineProcessor daoCommandListener() {
        DaoCommandProcessor listener = new DaoCommandProcessor();
        listener.setApplicationContext(applicationContext);
        return listener;
    }

    /**
     * 创建应用程序命令行处理器
     */
    @Bean
    public ICommandLineProcessor applicationExitCommandListener() {
        return new ApplicationExitCommandProcessor();
    }
}
