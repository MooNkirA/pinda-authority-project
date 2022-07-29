package com.moon.examples.springevent.controller;

import com.moon.examples.springevent.dto.LogDTO;
import com.moon.examples.springevent.event.SysLogEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-21 16:32
 * @description
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/getUser")
    public String getUser() {
        // 构造操作日志信息
        LogDTO logInfo = new LogDTO();
        logInfo.setRequestIp("127.0.0.1");
        logInfo.setUserName("admin");
        logInfo.setType("OPT");
        logInfo.setDescription("查询用户信息");

        // 构造事件对象
        ApplicationEvent event = new SysLogEvent(logInfo);

        // 发布日志事件
        applicationContext.publishEvent(event);

        long id = Thread.currentThread().getId();
        System.out.println("发布事件,线程id：" + id);
        return "OK";
    }

}
