package com.domain.food.config;

import com.domain.food.utils.JsonUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * web 配置
 *
 * @author zhoutaotao
 * @date 2019/5/15
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurationSupport {

    private ConfigProperties config;

    public WebMvcConfigurer(ConfigProperties config) {
        this.config = config;
    }

    /**
     * 配置接口请求方式拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserMvcInterceptor())
                .addPathPatterns("/user/**")
                .addPathPatterns("/product/**")
                .addPathPatterns("/order/**");
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

        String imagePath = config.getWeb().getImagePath();
        if (!imagePath.endsWith("/")) {
            imagePath = imagePath.concat("/");
        }
        // 注册其余资源映射
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("/static/")
                .addResourceLocations(imagePath);
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
        MappingJackson2HttpMessageConverter converter
                = new MappingJackson2HttpMessageConverter(JsonUtil.getMapper());
        converters.add(converter);
    }

    /**
     * 创建跨域过滤器
     * 只在运行环境为开发环境和测试环境时启用
     */
    @Bean
    @ConditionalOnEnvironment({Environment.DEVELOP, Environment.TEST})
    public FilterRegistrationBean<AllowOriginFilter> allowOriginFilter() {
        FilterRegistrationBean<AllowOriginFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new AllowOriginFilter());
        bean.addUrlPatterns("/*");
        bean.setName("AllowOriginFilter");
        bean.setOrder(1);
        return bean;
    }
}
