package com.moon.examples.swagger.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户实体
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-19 14:24
 * @description
 */
@Data
@ApiModel(value = "用户实体", description = "用户实体")
public class User {

    @ApiModelProperty(value = "主键")
    private int id;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "年龄")
    private int age;
    @ApiModelProperty(value = "地址")
    private String address;

}
