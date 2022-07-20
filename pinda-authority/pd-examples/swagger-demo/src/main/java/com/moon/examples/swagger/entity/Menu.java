package com.moon.examples.swagger.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 菜单实体
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-19 14:25
 * @description
 */
@Data
@ApiModel(value = "菜单实体", description = "菜单实体")
public class Menu {

    @ApiModelProperty(value = "主键")
    private int id;
    @ApiModelProperty(value = "菜单名称")
    private String name;

}
