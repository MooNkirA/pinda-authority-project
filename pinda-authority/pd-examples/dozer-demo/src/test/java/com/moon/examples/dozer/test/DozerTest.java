package com.moon.examples.dozer.test;

import com.github.dozermapper.core.Mapper;
import com.moon.examples.dozer.dto.UserDTO;
import com.moon.examples.dozer.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Dozer 基础测试
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-20 11:06
 * @description
 */
@SpringBootTest
public class DozerTest {

    // 使用 Dozer 提供的 Mapper 对象可以完成两个对象之间属性复制
    @Autowired
    private Mapper mapper;

    private UserDTO userDTO = new UserDTO();

    // 初始化测试使用的源对象值
    @BeforeEach
    public void init() {
        userDTO.setUserId("2828");
        userDTO.setUserName("MooNkirA");
        userDTO.setUserAge(21);
        userDTO.setAddress("GK-China");
        userDTO.setBirthday("2010-08-08");
    }

    // 根据类型 .class 进行复制
    @Test
    public void testDozer1() {
        UserEntity user = mapper.map(userDTO, UserEntity.class);
        System.out.println(user);
    }

    // 两个对象之间复制
    @Test
    public void testDozer2() {
        UserEntity user = new UserEntity();
        user.setId("200");
        System.out.println(user);
        mapper.map(userDTO, user);
        System.out.println(user);
    }

    // 根据配置的映射id 进行复制
    @Test
    public void testDozer3() {
        UserEntity user = new UserEntity();
        System.out.println(user);
        mapper.map(userDTO, user, "user");
        System.out.println(user);
    }

}
