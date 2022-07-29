package com.moon.examples.resolver.controller;

import com.moon.examples.resolver.annotation.CurrentUser;
import com.moon.examples.resolver.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制层
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-22 16:41
 * @description
 */
@RestController
@RequestMapping(value = "user")
public class UserController {

    // 获取当前系统登录用户
    @GetMapping
    public String getCurrentUser(@CurrentUser User user) {
        System.out.println("UserController getCurrentUser方法..." + user);
        return user.toString();
    }

}
