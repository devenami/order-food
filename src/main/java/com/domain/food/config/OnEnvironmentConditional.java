package com.domain.food.config;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 程序运行环境测试类
 *
 * @author zhoutaotao
 * @date 2019/5/31
 */
public class OnEnvironmentConditional extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        PropertyResolver resolver = context.getEnvironment();
        String property = resolver.getProperty("business.environment", Environment.PRODUCT.name());
        Environment environment = Environment.valueOf(property.toUpperCase());
        if (metadata.isAnnotated(ConditionalOnEnvironment.class.getName())) {
            Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(ConditionalOnEnvironment.class.getName());
            Environment[] envs = (Environment[]) annotationAttributes.get("value");
            List<Environment> envList = Arrays.asList(envs);
            if (envList.contains(environment)) {
                return new ConditionOutcome(true, "环境检查通过");
            }
            return new ConditionOutcome(false, "环境检查未通过");
        }
        return new ConditionOutcome(true, "no ConditionalOnEnvironment marked");
    }
}
