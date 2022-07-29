package com.moon.examples.antisamy.config;

import com.moon.examples.antisamy.filter.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，用于初始化自定义的过滤器对象
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-21 10:35
 * @description
 */
@Configuration
public class AntiSamyConfiguration {

    /**
     * 配置跨站攻击过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean(new XssFilter());
        filterRegistration.addUrlPatterns("/*"); // 设置过滤器拦截的 url 规则
        filterRegistration.setOrder(1); // 设置过滤器优先级
        return filterRegistration;
    }

}
