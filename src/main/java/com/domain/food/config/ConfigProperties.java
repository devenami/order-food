package com.domain.food.config;

import lombok.EqualsAndHashCode;
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

    @NestedConfigurationProperty
    private DB db = new DB();

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    public class DB {
        /**
         * 数据持久化时间
         */
        private int interval = 10;

        /**
         * 数据持久化路径
         */
        private String path = "classpath:/db";

        public String getFilePath(String file) {
            if (path.startsWith("classpath:")) {
                String relPath = path.substring(10);
                String classloaderPath = getClass().getClassLoader().getResource("").getPath();
                path = relPath.startsWith("/") ? classloaderPath + relPath.substring(1) : classloaderPath + relPath;
                file = file.startsWith("/") ? path + file.substring(1) : path + file;
            }
            return file;
        }

    }

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
