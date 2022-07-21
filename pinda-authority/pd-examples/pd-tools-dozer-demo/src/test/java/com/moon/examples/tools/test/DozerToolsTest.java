package com.moon.examples.tools.test;

import com.github.dozermapper.core.Mapper;
import com.moon.examples.tools.dto.UserDTO;
import com.moon.examples.tools.entity.UserEntity;
import com.moon.pinda.dozer.DozerUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * pd-tools-dozer 模块使用测试
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-20 13:44
 * @description
 */
@SpringBootTest
public class DozerToolsTest {

    // 在 pd-tools-dozer 中已经完成了自动配置，可以直接注入
    @Autowired
    private DozerUtils dozerUtils;

    private UserDTO userDTO = new UserDTO();

    // 初始化测试使用的源对象值
    @BeforeEach
    public void init() {
        userDTO.setUserId("123123");
        userDTO.setUserName("MooNkirA");
        userDTO.setUserAge(22);
        userDTO.setAddress("ABC");
        userDTO.setBirthday("2020-08-08");
    }

    // 根据类型 .class 进行复制
    @Test
    public void testDozerUtils() {
        UserEntity user = dozerUtils.map(userDTO, UserEntity.class);
        System.out.println(user);
    }

}
