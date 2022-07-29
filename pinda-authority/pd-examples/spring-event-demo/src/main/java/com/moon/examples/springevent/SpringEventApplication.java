package com.moon.examples.springevent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-21 16:30
 * @description
 */
@SpringBootApplication
@EnableAsync // 启用异步处理
public class SpringEventApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringEventApplication.class, args);
    }

}
