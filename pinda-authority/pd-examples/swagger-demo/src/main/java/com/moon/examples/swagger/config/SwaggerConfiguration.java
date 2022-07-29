package com.moon.examples.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger 配置类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-19 14:47
 * @description
 */
@Configuration
@EnableSwagger2 // 开启 Swagger
public class SwaggerConfiguration {

    // 模拟创建多个文档组 - 用户模块
    @Bean
    public Docket createRestApi1() {
        // docket对象用于封装接口文档相关信息
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("用户接口组").select()
                .apis(RequestHandlerSelectors.basePackage("com.moon.examples.swagger.controller.user"))
                .build();
    }

    // 模拟创建多个文档组 - 用户模块
    @Bean
    public Docket createRestApi2() {
        // docket对象用于封装接口文档相关信息
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("菜单接口组").select()
                .apis(RequestHandlerSelectors.basePackage("com.moon.examples.swagger.controller.menu"))
                .build();
    }

    /**
     * 添加摘要信息
     */
    private ApiInfo apiInfo() {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                .title("我的接口文档")
                .contact(new Contact("MooNkirA", "http://www.moon.com", "hello@moon.com")) // 设置作者
                .version("1.0") // 设置版本
                .description("接口文档描述") // 设置描述
                .build();
    }

}
