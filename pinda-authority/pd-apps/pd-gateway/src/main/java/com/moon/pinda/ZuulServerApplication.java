package com.moon.pinda;

import com.moon.pinda.auth.client.EnableAuthClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 网关服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient // 开启注册中心客户端
@EnableFeignClients({"com.moon.pinda"}) // 开启 feign 客户端
@EnableZuulProxy // 开启网关代理
@EnableAuthClient // 开启授权客户端，开启后即可使用 pd-tools-jwt 提供的工具类进行 jwt token 解析了
public class ZuulServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulServerApplication.class, args);
    }
}
