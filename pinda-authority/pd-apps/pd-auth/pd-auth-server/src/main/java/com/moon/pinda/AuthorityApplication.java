package com.moon.pinda;

import com.moon.pinda.auth.server.EnableAuthServer;
import com.moon.pinda.user.annotation.EnableLoginArgResolver;
import com.moon.pinda.validator.config.EnableFormValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 权限服务启动类
 */
@Slf4j
@SpringBootApplication
@EnableDiscoveryClient // 开启注册中心客户端
@EnableAuthServer // pd-tools-jwt 模块，启用认证服务的服务端配置
@EnableFeignClients(value = {"com.moon.pinda"}) // 开启 feign 客户端
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableLoginArgResolver // pd-tools-user 模块，开启自动登录用户对象注入，将 Token 转化 SysUser 对象
@EnableFormValidator // pd-tools-validator 模块，启动表单验证功能
public class AuthorityApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext context = SpringApplication.run(AuthorityApplication.class, args);
        // 获取环境对象
        ConfigurableEnvironment environment = context.getEnvironment();
        // 读取相关的配置
        String appName = environment.getProperty("spring.application.name");
        String port = environment.getProperty("server.port");
        String hostAddress = InetAddress.getLocalHost().getHostAddress();

        // 启动完成后在控制台提示项目启动成功，并且输出当前服务对应的swagger接口文档访问地址
        log.info("应用 {} 启动成功!swagger 文档地址：http://{}:{}/doc.html", appName, hostAddress, port);
    }

}
