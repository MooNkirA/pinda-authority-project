package com.moon.pinda.authority.config;

import com.moon.pinda.common.handler.DefaultGlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 权限服务中使用的全局异常处理配置类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-25 11:21
 * @description
 */
@Configuration
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
public class ExceptionConfiguration extends DefaultGlobalExceptionHandler {
}
