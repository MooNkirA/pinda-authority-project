package com.moon.pinda.authority.controller.auth;

import com.moon.pinda.authority.biz.service.auth.AuthManager;
import com.moon.pinda.authority.biz.service.auth.ValidateCodeService;
import com.moon.pinda.authority.dto.auth.LoginDTO;
import com.moon.pinda.authority.dto.auth.LoginParamDTO;
import com.moon.pinda.base.BaseController;
import com.moon.pinda.base.R;
import com.moon.pinda.exception.BizException;
import com.moon.pinda.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @Autowired
    private AuthManager authManager;

    /**
     * 生成验证码
     *
     * @param key
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/captcha", produces = "image/png")
    @ApiOperation(notes = "验证码", value = "验证码")
    @SysLog("生成验证码")
    public void captcha(@RequestParam(value = "key") String key, HttpServletResponse response) throws IOException {
        validateCodeService.create(key, response);
    }

    /**
     * 用户登陆
     *
     * @param loginParamDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(notes = "用户登录", value = "用户登录")
    @SysLog("用户登录")
    public R<LoginDTO> login(@Validated @RequestBody LoginParamDTO loginParamDTO) {
        // 校验验证码是否正确
        boolean check = validateCodeService.check(loginParamDTO.getKey(), loginParamDTO.getCode());
        // 验证码校验不通过，直接返回
        if (!check) {
            throw BizException.validFail("请输入正常的验证码");
        }
        // 验证码校验通过，执行具体的登录认证逻辑
        LoginDTO dto = authManager.login(loginParamDTO.getAccount(), loginParamDTO.getPassword());
        return this.success(dto);
    }

    /**
     * 校验验证码
     *
     * @param loginParamDTO
     * @return
     */
    @PostMapping("/check")
    @ApiOperation(notes = "校验验证码", value = "校验验证码")
    public boolean check(@RequestBody LoginParamDTO loginParamDTO) {
        // 校验验证码是否正确
        return validateCodeService.check(loginParamDTO.getKey(), loginParamDTO.getCode());
    }
}
