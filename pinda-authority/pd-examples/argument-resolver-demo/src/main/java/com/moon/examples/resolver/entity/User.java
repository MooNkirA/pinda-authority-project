package com.moon.examples.resolver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用户实体
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-22 16:24
 * @description
 */
@Data
@AllArgsConstructor
public class User {

    private Long id;
    private String username;

}
