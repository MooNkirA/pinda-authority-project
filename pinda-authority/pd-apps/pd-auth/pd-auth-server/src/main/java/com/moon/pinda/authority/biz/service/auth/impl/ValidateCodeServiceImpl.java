package com.moon.pinda.authority.biz.service.auth.impl;

import com.moon.pinda.authority.biz.service.auth.ValidateCodeService;
import com.moon.pinda.common.constant.CacheKey;
import com.moon.pinda.exception.BizException;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.ChineseCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码服务
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-25 15:34
 * @description
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Autowired
    private CacheChannel cache;

    /**
     * 生成验证码，并使用 j2cache 缓存验证码
     *
     * @param key      验证码 uuid
     * @param response HttpServletResponse
     * @throws IOException
     */
    @Override
    public void create(String key, HttpServletResponse response) throws IOException {
        // 校验
        if (StringUtils.isBlank(key)) {
            throw BizException.validFail("验证码key不能为空");
        }

        // 设置响应的类型
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        // 设置响应头，防止浏览器缓存
        response.setHeader(HttpHeaders.PRAGMA, "No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "No-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);

        // 生成算术验证码对象
        Captcha captcha = new ArithmeticCaptcha(115, 42);
        captcha.setCharType(2);

        // 设置缓存的区域、key、缓存的相应验证码的值
        cache.set(CacheKey.CAPTCHA, key, StringUtils.lowerCase(captcha.text()));
        // 使用输出流将图片响应给前端页面
        captcha.out(response.getOutputStream());
    }

    /**
     * 校验验证码
     *
     * @param key   前端上送 key
     * @param value 前端上送待校验值
     */
    @Override
    public boolean check(String key, String value) {
        // 校验
        if (StringUtils.isBlank(key)) {
            throw BizException.validFail("请输入验证码");
        }

        // 根据 key 从缓存中获取验证码
        CacheObject cacheObject = cache.get(CacheKey.CAPTCHA, key);
        Object captcha = cacheObject.getValue();
        // 缓存中无值，则代表 key 已过期
        if (captcha == null) {
            throw BizException.validFail("验证码已过期");
        }

        // 比较是否一致
        if (!StringUtils.equalsIgnoreCase(value, String.valueOf(captcha))) {
            throw BizException.validFail("验证码不正确");
        }

        // TODO: 目前是验证通过，立即从缓存中删除验证码。待优化，其实不合理
        cache.evict(CacheKey.CAPTCHA, key);
        return true;
    }

    // 暂无使用
    private Captcha createCaptcha(String type) {
        Captcha captcha = null;
        if (StringUtils.equalsIgnoreCase(type, "gif")) {
            captcha = new GifCaptcha(115, 42, 4);
        } else if (StringUtils.equalsIgnoreCase(type, "png")) {
            captcha = new SpecCaptcha(115, 42, 4);
        } else if (StringUtils.equalsIgnoreCase(type, "arithmetic")) {
            captcha = new ArithmeticCaptcha(115, 42);
        } else if (StringUtils.equalsIgnoreCase(type, "chinese")) {
            captcha = new ChineseCaptcha(115, 42);
        }
        captcha.setCharType(2);
        return captcha;
    }

    // 暂无使用
    private void setHeader(HttpServletResponse response, String type) {
        if (StringUtils.equalsIgnoreCase(type, "gif")) {
            response.setContentType(MediaType.IMAGE_GIF_VALUE);
        } else {
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        response.setHeader(HttpHeaders.PRAGMA, "No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "No-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);
    }
}
