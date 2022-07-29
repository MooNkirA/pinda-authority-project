package com.moon.examples.tools.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-21 11:26
 * @description
 */
@RestController
@RequestMapping("/xss")
public class XSSController {

    @GetMapping("/get")
    public String get(String text) {
        return "处理之后的文本内容为：" + text;
    }

}
