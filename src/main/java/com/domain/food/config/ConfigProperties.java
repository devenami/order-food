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

    private Environment environment = Environment.PRODUCT;

    @NestedConfigurationProperty
    private final Web web = new Web();

    @NestedConfigurationProperty
    private final DB db = new DB();

    @Getter
    @Setter
    @NoArgsConstructor
    public static class DB {
        /**
         * 数据持久化时间
         */
        private int interval = 1;

        /**
         * 缓存过期时间
         */
        private int expireTime = 5;

        /**
         * 数据持久化路径
         */
        private String path = "classpath:/db";
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class Web {

        /**
         * 是否打印堆栈信息， 开发环境可以开启
         */
        private boolean showStack;

        /**
         * 错误码和错误页面地址的对应关系
         * 404 -> classpath:/shutdown/404.html
         * 500 -> http://www.domain.com/error.html
         */
        private Map<Integer, String> errorPath = new HashMap<>();

        /**
         * 图片存储地址
         */
        private String imagePath = "classpath:/images";
    }

}
