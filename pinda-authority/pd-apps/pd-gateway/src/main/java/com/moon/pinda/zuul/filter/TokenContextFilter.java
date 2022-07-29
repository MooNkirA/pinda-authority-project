package com.moon.pinda.zuul.filter;

import com.moon.pinda.auth.client.properties.AuthClientProperties;
import com.moon.pinda.auth.client.utils.JwtTokenClientUtils;
import com.moon.pinda.auth.utils.JwtUserInfo;
import com.moon.pinda.base.R;
import com.moon.pinda.context.BaseContextConstants;
import com.moon.pinda.exception.BizException;
import com.moon.pinda.utils.StrHelper;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * JWT 解析过滤器。
 * <p>
 * 当前过滤器负责解析请求头中的 jwt 令牌并且将解析出的用户信息放入 zuul 上下文的 header 中
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-27 15:08
 * @description
 */
@Component
public class TokenContextFilter extends BaseFilter {

    @Autowired
    private AuthClientProperties authClientProperties;
    @Autowired
    private JwtTokenClientUtils jwtTokenClientUtils;

    // 设置过滤器类型
    @Override
    public String filterType() {
        // 前置过滤器 "pre"
        return FilterConstants.PRE_TYPE;
    }

    // 设置过滤器执行顺序，数值越大优先级越低
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
    }

    // 是否执行当前过滤器，目前是直接执行
    @Override
    public boolean shouldFilter() {
        return true;
    }

    // 过滤器真正的处理逻辑
    @Override
    public Object run() throws ZuulException {
        if (isIgnoreToken()) {
            // uri 在忽略校验列表中，直接放行
            return null;
        }
        // 获取 zuul 请求的上下文对象
        RequestContext ctx = RequestContext.getCurrentContext();
        // 获取当前请求对象
        HttpServletRequest request = ctx.getRequest();

        // 从请求头中获取前端提交的jwt令牌
        String userToken = request.getHeader(authClientProperties.getUser().getHeaderName());

        try {
            // 通过 pd-tools-jwt 模块的 JwtToken 客户端工具解析 jwt 令牌
            JwtUserInfo userInfo = jwtTokenClientUtils.getUserInfo(userToken);
            // 将解析出来的用户信息放入 header
            if (userInfo != null) {
                addHeader(ctx, BaseContextConstants.JWT_KEY_ACCOUNT, userInfo.getAccount());
                addHeader(ctx, BaseContextConstants.JWT_KEY_USER_ID, userInfo.getUserId());
                addHeader(ctx, BaseContextConstants.JWT_KEY_NAME, userInfo.getName());
                addHeader(ctx, BaseContextConstants.JWT_KEY_ORG_ID, userInfo.getOrgId());
                addHeader(ctx, BaseContextConstants.JWT_KEY_STATION_ID, userInfo.getStationId());
            }
        } catch (BizException e) {
            errorResponse(e.getMessage(), e.getCode(), 200);
        } catch (Exception e) {
            errorResponse("解析jwt令牌出错", R.FAIL_CODE, 200);
        }

        return null;
    }

    // 将指定信息放入 zuul 上下文的 header 中
    private void addHeader(RequestContext ctx, String name, Object value) {
        if (!StringUtils.isEmpty(value)) {
            ctx.addZuulRequestHeader(name, StrHelper.encode(value.toString()));
        }
    }
}
