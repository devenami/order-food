package com.domain.food.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置文件
 *
 * @author zhoutaotao
 * @date 2019/5/15
 */
@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("business")
public class ConfigProperties {

    @NestedConfigurationProperty
    private Web web = new Web();

    @Setter
    @Getter
    @NoArgsConstructor
    public class Web {

        /**
         * 是否打印堆栈信息， 开发环境可以开启
         */
        private boolean showStack;

        /**
         * 错误码和错误页面地址的对应关系
         * 404 -> classpath:/error/404.html
         * 500 -> http://www.domain.com/error.html
         */
        private Map<Integer, String> errorPath = new HashMap<>();
    }

}
