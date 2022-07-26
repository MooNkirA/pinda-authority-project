package com.moon.pinda.authority.biz.service.auth;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码业务接口
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-25 15:32
 * @description
 */
public interface ValidateCodeService {

    /**
     * 生成验证码
     *
     * @param key      验证码 uuid
     * @param response HttpServletResponse
     * @throws IOException
     */
    void create(String key, HttpServletResponse response) throws IOException;

}
