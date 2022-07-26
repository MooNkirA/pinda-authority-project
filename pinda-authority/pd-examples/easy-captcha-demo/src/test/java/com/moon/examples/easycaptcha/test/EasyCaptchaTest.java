package com.moon.examples.easycaptcha.test;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.ChineseCaptcha;
import com.wf.captcha.base.Captcha;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * easy-captcha 生成验证码基础测试
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-25 15:01
 * @description
 */
public class EasyCaptchaTest {

    // 中文验证码测试
    @Test
    public void testChineseCaptcha() throws FileNotFoundException {
        // 获取中文验证码对象，可以指定图片的长与宽
        Captcha captcha = new ChineseCaptcha(150, 60);
        // 获取本次生成的验证码
        System.out.println("生成中文验证码：" + captcha.text());
        // 获取成验证码图片
        captcha.out(new FileOutputStream(new File(("E:\\ChineseCaptcha.png"))));
    }

    // 算术验证码测试
    @Test
    public void testArithmeticCaptcha() throws FileNotFoundException {
        // 获取算术验证码对象，可以在构造方法中指定图片的长与宽
        Captcha captcha = new ArithmeticCaptcha();
        // 获取本次生成的验证码
        System.out.println("生成算术验证码：" + captcha.text());
        // 获取成验证码图片
        captcha.out(new FileOutputStream(new File(("E:\\ArithmeticCaptcha.png"))));
    }

}
