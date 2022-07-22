package com.moon.examples.resolver.resolver;

import com.moon.examples.resolver.annotation.CurrentUser;
import com.moon.examples.resolver.entity.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 自定义参数解析器。
 * 动态为 Controller 方法注入当前登录用户对象
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-22 16:27
 * @description
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 判断当前参数是否需要解析
     *
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 判断当前 Controller 的方法形参类型是否为 User 并且标识 @CurrentUser 注解
        return parameter.getParameterType().equals(User.class) && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    /**
     * 当 supportsParameter 方法返回 true 时执行此方法进行参数解析绑定
     *
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        System.out.println("参数解析器方法执行了...");
        // 实际项目中可能需要从请求头中获取登录用户的令牌然后进行解析，最终封装成 User 对象返回。
        // 这里只模拟硬编码返回用户对象，这样在 Controller 的方法形参就可以直接引用到此 User 对象
        return new User(1L, "admin");
    }
}
