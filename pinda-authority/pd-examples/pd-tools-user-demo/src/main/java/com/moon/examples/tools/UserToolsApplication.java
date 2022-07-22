package com.moon.examples.tools;

import com.moon.pinda.user.annotation.EnableLoginArgResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-22 17:24
 * @description
 */
@SpringBootApplication
@EnableLoginArgResolver // 开启自动登录用户对象注入
public class UserToolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserToolsApplication.class, args);
    }

}