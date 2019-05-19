package com.domain.food.config;

import com.domain.food.utils.JsonUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.util.List;

/**
 * web 配置
 *
 * @author zhoutaotao
 * @date 2019/5/15
 */
@Configuration
public class WebMvcConfigurer implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {

    /**
     * 配置接口请求方式拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }

    /**
     * 配置资源映射器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 注册所有的 html 文件转发到指定文件夹下面
        registry.addResourceHandler("/**/*.html")
                .addResourceLocations("classpath:/html/")
                .addResourceLocations("/html/");
        // 注册swagger资源映射
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 配置 mvc 方法参数解析器
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserArgumentResolver());
    }

    /**
     * 配置消息转换器，用于将java与json之间的数据转换
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(JsonUtil.getMapper());
        converters.add(converter);
    }
}
