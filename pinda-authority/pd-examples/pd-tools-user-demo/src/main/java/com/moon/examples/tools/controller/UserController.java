package com.moon.examples.tools.controller;

import com.moon.pinda.user.annotation.LoginUser;
import com.moon.pinda.user.model.SysUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-22 17:25
 * @description
 */
@RestController
@RequestMapping(value = "user")
public class UserController {

    // 获取当前系统登录用户
    @GetMapping
    public SysUser getCurrentUser(@LoginUser SysUser user) {
        System.out.println("UserController getCurrentUser方法..." + user);
        return user;
    }

}