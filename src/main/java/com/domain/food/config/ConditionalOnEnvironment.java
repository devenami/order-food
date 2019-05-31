package com.domain.food.config;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * 开发环境启用
 *
 * @author zhoutaotao
 * @date 2019/5/31
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnEnvironmentConditional.class)
public @interface ConditionalOnEnvironment {

    /**
     * 当前条件支持的环境列表
     */
    Environment[] value() default Environment.PRODUCT;

}
