package com.moon.examples.knife4j.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * knife4j 配置类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-19 16:09
 * @description
 */
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class) // 引入配置映射类
@EnableSwagger2 // 开启 swagger
// 当 example.swagger.enabled 值为 true 时，才加载当前配置类
@ConditionalOnProperty(name = "example.swagger.enabled", havingValue = "true", matchIfMissing = true)
public class Knife4jConfiguration implements BeanFactoryAware {

    private static final String BASE_PATH = "/**";

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Autowired
    private SwaggerProperties swaggerProperties;

    // 因为此方法返回是 List 对象集合，所以需要使用到 BeanFactory 来辅助注册集合中的实例到 Spring 容器
    @Bean
    @ConditionalOnMissingBean // 配置当容器中不存在该bean时，才创建
    public List<Docket> createRestApi() {
        ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;
        List<Docket> docketList = new LinkedList<>();
        // 获取配置中的分组数据
        Map<String, SwaggerProperties.DocketInfo> docketListConfig = swaggerProperties.getDocket();

        if (docketListConfig.isEmpty()) {
            // 没有分组
            Docket docket = createDocket(swaggerProperties);
            // 手动注册 Docket 实例到 Spring 容器
            configurableBeanFactory.registerSingleton(swaggerProperties.getTitle(), docket);
            docketList.add(docket);
        } else {
            // 存在分组
            Set<Map.Entry<String, SwaggerProperties.DocketInfo>> entries = docketListConfig.entrySet();

            for (Map.Entry<String, SwaggerProperties.DocketInfo> entry : entries) {
                SwaggerProperties.DocketInfo docketInfo = entry.getValue();
                ApiInfo apiInfo = new ApiInfoBuilder()
                        //页面标题
                        .title(docketInfo.getTitle())
                        //创建人
                        .contact(new Contact(docketInfo.getContact().getName(),
                                docketInfo.getContact().getUrl(),
                                docketInfo.getContact().getEmail()))
                        //版本号
                        .version(docketInfo.getVersion())
                        //描述
                        .description(docketInfo.getDescription())
                        .build();
                // base-path处理
                // 当没有配置任何path的时候，解析/**
                if (docketInfo.getBasePath().isEmpty()) {
                    docketInfo.getBasePath().add(BASE_PATH);
                }
                List<Predicate<String>> basePath = new ArrayList<>();
                for (String path : docketInfo.getBasePath()) {
                    basePath.add(PathSelectors.ant(path));
                }

                // exclude-path处理
                List<Predicate<String>> excludePath = new ArrayList<>();
                for (String path : docketInfo.getExcludePath()) {
                    excludePath.add(PathSelectors.ant(path));
                }

                Docket docket = new Docket(DocumentationType.SWAGGER_2)
                        .apiInfo(apiInfo)
                        .groupName(docketInfo.getGroup())
                        .select()
                        //为当前包路径
                        .apis(RequestHandlerSelectors.basePackage(docketInfo.getBasePackage()))
                        .paths(Predicates.and(Predicates.not(Predicates.or(excludePath)), Predicates.or(basePath)))
                        .build();
                // 手动注册 Docket 实例到 Spring 容器
                configurableBeanFactory.registerSingleton(entry.getKey(), docket);
                docketList.add(docket);
            }
        }
        return docketList;
    }

    // 创建接口文档对象
    private Docket createDocket(SwaggerProperties swaggerProperties) {
        SwaggerProperties.Contact contact = swaggerProperties.getContact();

        // 构建 api文档的详细信息
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())  // 页面标题
                .contact(new Contact(contact.getName(), contact.getUrl(), contact.getEmail()))  // 创建人
                .version(swaggerProperties.getVersion())  // 版本号
                .description(swaggerProperties.getDescription())// 描述
                .build();

        // base-path 处理
        List<String> basePathConfig = swaggerProperties.getBasePath();
        if (basePathConfig.isEmpty()) {
            // 当没有配置任何path的时候，解析 /**
            basePathConfig.add(BASE_PATH);
        }

        List<Predicate<String>> basePath = new ArrayList<>();
        for (String path : basePathConfig) {
            basePath.add(PathSelectors.ant(path));
        }

        // exclude-path 处理
        List<Predicate<String>> excludePath = new ArrayList<>();
        for (String path : swaggerProperties.getExcludePath()) {
            excludePath.add(PathSelectors.ant(path));
        }

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName(swaggerProperties.getGroup())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .paths(Predicates.and(Predicates.not(Predicates.or(excludePath)), Predicates.or(basePath)))
                .build();
    }
}
