package com.moon.examples.resolver.config;

import com.moon.examples.resolver.resolver.CurrentUserMethodArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Spring MVC 配置类，用于注册自定义参数解析器
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-22 16:36
 * @description
 */
@Configuration
public class ArgumentResolverConfiguration implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CurrentUserMethodArgumentResolver());
    }

}
