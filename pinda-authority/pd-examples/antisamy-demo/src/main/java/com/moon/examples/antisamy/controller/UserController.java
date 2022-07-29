package com.moon.examples.antisamy.controller;

import com.moon.examples.antisamy.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-21 9:58
 * @description
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/save")
    public String save(User user) {
        System.out.println("UserController save.... " + user);
        return user.getName();
    }

}
