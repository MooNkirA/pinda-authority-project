package com.moon.examples.springevent.dto;

import lombok.Data;

/**
 * 封装用户操作日志信息实体类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-21 16:32
 * @description
 */
@Data
public class LogDTO {

    private String requestIp; // 操作IP
    private String type; // 日志类型 LogType{OPT:操作类型;EX:异常类型}
    private String userName; // 操作人
    private String description; // 操作描述

}
