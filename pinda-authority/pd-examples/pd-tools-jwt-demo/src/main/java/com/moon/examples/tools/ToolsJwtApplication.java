package com.moon.examples.tools;

import com.moon.pinda.auth.server.EnableAuthServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-22 15:45
 * @description
 */
@SpringBootApplication
@EnableAuthServer // 启用 jwt 服务端认证功能
public class ToolsJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToolsJwtApplication.class, args);
    }

}
