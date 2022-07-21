package com.moon.examples.antisamy.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 自定义过滤器，用于过滤所有提交到服务器的请求参数
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-21 10:20
 * @description
 */
public class XssFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        /*
         * 调用 FilterChain 对象放行方法。
         * 注意：这里将 ServletRequest 对象包装成自定义的 XssRequestWrapper 对象
         * XssRequestWrapper 继承 HttpServletRequestWrapper 类，而真正的参数校验与过滤在此包装类中完成
         */
        chain.doFilter(new XssRequestWrapper(httpServletRequest), response);
    }
}
