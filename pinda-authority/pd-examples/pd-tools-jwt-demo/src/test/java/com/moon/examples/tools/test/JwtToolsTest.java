package com.moon.examples.tools.test;

import com.moon.pinda.auth.server.utils.JwtTokenServerUtils;
import com.moon.pinda.auth.utils.JwtUserInfo;
import com.moon.pinda.auth.utils.Token;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * pd-tools-jwt 基础测试
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-22 15:47
 * @description
 */
@SpringBootTest
public class JwtToolsTest {

    @Autowired
    private JwtTokenServerUtils jwtTokenServerUtils;

    // 生成与解析 JWT 令牌测试
    @Test
    public void testBasic() {
        // 模拟查询数据库进行用户名密码校验...
        String userName = "admin";
        String password = "admin123";

        // 如果校验通过，则为客户端生成jwt令牌
        JwtUserInfo jwtUserInfo = new JwtUserInfo();
        jwtUserInfo.setName(userName);
        jwtUserInfo.setOrgId(10L);
        jwtUserInfo.setUserId(1L);
        jwtUserInfo.setAccount(userName);
        jwtUserInfo.setStationId(20L);
        Token token = jwtTokenServerUtils.generateUserToken(jwtUserInfo, null);
        System.out.println("*************** token ***************");
        System.out.println(token);

        // 实际应该是在过滤器中进行jwt令牌的解析
        JwtUserInfo userInfo = jwtTokenServerUtils.getUserInfo(token.getToken());
        System.out.println("*************** JwtUserInfo ***************");
        System.out.println(userInfo);
    }

}
