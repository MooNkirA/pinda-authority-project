package com.moon.examples.dozer.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-20 10:52
 * @description
 */
@Data
public class UserEntity {

    private String id;
    private String name;
    private int age;
    private String address;
    private Date birthday;

}
