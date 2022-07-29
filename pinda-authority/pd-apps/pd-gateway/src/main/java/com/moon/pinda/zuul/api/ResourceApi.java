package com.moon.pinda.zuul.api;

import com.moon.pinda.authority.dto.auth.ResourceQueryDTO;
import com.moon.pinda.authority.entity.auth.Resource;
import com.moon.pinda.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 资源服务 feign 远程代理接口
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-27 11:10
 * @description
 */
/*
 * @FeignClient 注解，用于标识当前接口为Feign调用微服务的核心接口
 *  value/name属性：指定需要调用的服务提供者的名称，从 nacos 配置中心读取，默认值为 pd-auth-server
 *  fallback 属性：指定服务熔断处理类
 */
@FeignClient(name = "${pinda.feign.authority-server:pd-auth-server}",
        fallback = ResourceApiFallback.class)
public interface ResourceApi {

    //获取所有需要鉴权的资源
    @GetMapping("/resource/list")
    R<List> list();

    //查询当前登录用户拥有的资源权限
    @GetMapping("/resource")
    R<List<Resource>> visible(ResourceQueryDTO resource);

}
