package com.moon.pinda.authority.biz.service.auth;

import com.moon.pinda.auth.utils.Token;
import com.moon.pinda.authority.dto.auth.LoginDTO;
import com.moon.pinda.authority.entity.auth.User;

/**
 * 认证管理器
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-26 10:41
 * @description
 */
public interface AuthManager {

    /**
     * 账户认证
     *
     * @param account
     * @param password
     * @return
     */
    LoginDTO login(String account, String password);

    /**
     * 校验账户与密码，通过后用户对象
     *
     * @param account
     * @param password
     * @return
     */
    User check(String account, String password);

    /**
     * 为用户生成对应的jwt令牌
     *
     * @param user 用户实例
     * @return
     */
    Token generateUserToken(User user);
}
