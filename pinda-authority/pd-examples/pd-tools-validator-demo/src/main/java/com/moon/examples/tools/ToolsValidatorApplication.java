package com.moon.examples.tools;

import com.moon.pinda.validator.config.EnableFormValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-20 22:43
 * @description
 */
@SpringBootApplication
// @EnableFormValidator // 开启校验快速失败模式
public class ToolsValidatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToolsValidatorApplication.class, args);
    }

}
