package com.moon.examples.tools.controller;

import com.moon.examples.tools.entity.UserDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * 测试控制类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-20 22:47
 * @description
 */
@RestController
@RequestMapping("/user")
@Validated // 开启校验功能
public class UserController {

    // 简单数据类型校验
    @RequestMapping("/delete")
    public String delete(@NotBlank(message = "id不能为空") String id) {
        System.out.println("delete..." + id);
        return "OK";
    }

    // 对象属性校验
    @RequestMapping("/save")
    public String save(@Validated UserDTO user) {
        System.out.println("save..." + user);
        return "OK";
    }

}
