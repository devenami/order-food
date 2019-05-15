package com.domain.food.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

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
    class Web {

        /**
         * 是否打印堆栈信息， 开发环境可以开启
         */
        private boolean showStack;

        /**
         * 404 错误页面的路径
         * 使用项目内部的页面，需要添加 "classpath:" 前缀
         */
        private String page404;

        /**
         * 500 错误页面的路径
         * 使用项目内部的页面，需要添加 "classpath:" 前缀
         */
        private String page500;

        /**
         * 获取 404 错误页面的绝对地址
         */
        public String getPage404() {
            return (page404 = getAbsolutePath(page404));
        }

        /**
         * 获取 500 错误页面
         */
        public String getPage500() {
            return (page500 = getAbsolutePath(page500));
        }
    }


    public String getAbsolutePath(String filename) {
        if (filename.startsWith("classpath:")) {
            String classloaderRootPath = getClass().getClassLoader().getResource("").getPath();
            String relativePath = filename.substring(10);
            relativePath = relativePath.startsWith("/") ? relativePath.substring(1) : relativePath;
            filename = classloaderRootPath + relativePath;
        }
        return filename;
    }

}
