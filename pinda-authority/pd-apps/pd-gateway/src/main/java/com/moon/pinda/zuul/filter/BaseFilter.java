package com.moon.pinda.zuul.filter;

import cn.hutool.core.util.StrUtil;
import com.moon.pinda.base.R;
import com.moon.pinda.common.adapter.IgnoreTokenConfig;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础过滤器
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-27 14:24
 * @description
 */
public abstract class BaseFilter extends ZuulFilter {

    // 获取配置的网关服务请求根路径，示例配置是 /api
    @Value("${server.servlet.context-path}")
    protected String zuulPrefix;

    // 判断当前请求uri是否需要忽略（直接放行）
    protected boolean isIgnoreToken() {
        // 动态获取当前请求的uri
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        String uri = request.getRequestURI();
        // 对 uri 进行截取，如 /api/authority/menu/page --> /menu/page
        uri = StrUtil.subSuf(uri, zuulPrefix.length());
        uri = StrUtil.subSuf(uri, uri.indexOf("/", 1));
        // 判断是否包含在忽略url集合中
        return IgnoreTokenConfig.isIgnoreToken(uri);
    }

    // 网关抛异常，不再进行路由，而是直接返回到前端
    protected void errorResponse(String errMsg, int errCode, int httpStatusCode) {
        RequestContext ctx = RequestContext.getCurrentContext();
        // 设置响应状态码
        ctx.setResponseStatusCode(httpStatusCode);
        // 设置响应头信息
        ctx.addZuulResponseHeader("Content-Type", "application/json;charset=utf-8");
        if (ctx.getResponseBody() == null) {
            // 设置响应体
            ctx.setResponseBody(R.fail(errCode, errMsg).toString());
            // 不进行路由，直接返回
            ctx.setSendZuulResponse(false);
        }
    }

}
