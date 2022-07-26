package com.moon.pinda.authority.controller.auth;

import com.moon.pinda.authority.biz.service.auth.ValidateCodeService;
import com.moon.pinda.base.BaseController;
import com.moon.pinda.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录控制类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-25 15:29
 * @description
 */
@RestController
@RequestMapping("/anno")
@Api(tags = "登录控制器", value = "LoginController")
public class LoginController extends BaseController {

    @Autowired
    private ValidateCodeService validateCodeService;

    /**
     * 生成验证码
     *
     * @param key
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/captcha", produces = "image/png")
    @ApiOperation(notes = "验证码", value = "验证码")
    public void captcha(@RequestParam(value = "key") String key, HttpServletResponse response) throws IOException {
        validateCodeService.create(key, response);
    }

}
