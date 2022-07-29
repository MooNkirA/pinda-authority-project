package com.moon.pinda.zuul.filter;

import cn.hutool.core.util.StrUtil;
import com.moon.pinda.authority.dto.auth.ResourceQueryDTO;
import com.moon.pinda.authority.entity.auth.Resource;
import com.moon.pinda.common.constant.CacheKey;
import com.moon.pinda.context.BaseContextConstants;
import com.moon.pinda.exception.code.ExceptionCode;
import com.moon.pinda.zuul.api.ResourceApi;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 鉴权处理过滤器
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-27 16:10
 * @description
 */
@Component
@Slf4j
public class AccessFilter extends BaseFilter {

    @Autowired
    private ResourceApi resourceApi;
    @Autowired
    private CacheChannel cache;

    // 设置过滤器类型
    @Override
    public String filterType() {
        // 前置过滤器 "pre"
        return FilterConstants.PRE_TYPE;
    }

    // 设置过滤器执行顺序，数值越大优先级越低
    @Override
    public int filterOrder() {
        // 此处设置比 JWT 令牌解析过滤器 TokenContextFilter 的数值高即可
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 10;
    }

    // 是否执行当前过滤器，目前是直接执行
    @Override
    public boolean shouldFilter() {
        return true;
    }

    // 过滤器真正的处理逻辑
    @Override
    public Object run() throws ZuulException {
        // 第1步：判断当前请求 uri 是否需要忽略
        if (isIgnoreToken()) {
            // 当前请求需要忽略，直接放行
            return null;
        }

        // 第2步：获取当前请求的请求方式和 uri，拼接成 GET/user/page 格式，称为权限标识符
        // 通过 zuul 请求上下文获取当前请求对象
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        // 获取当前请求方式，如：GET POST PUT
        String method = request.getMethod();
        // 获取请求的 url
        String uri = request.getRequestURI();
        // 对 uri 进行截取，去掉请求的网关与服务相应的前缀。如 /api/authority/menu/page --> /menu/page
        uri = StrUtil.subSuf(uri, zuulPrefix.length());
        uri = StrUtil.subSuf(uri, uri.indexOf("/", 1));
        // 拼接成 GET/user/page 这种格式，称为权限标识符
        String permission = method + uri;

        // 第3步：从缓存中获取所有需要进行鉴权的资源(同样是由资源表的method字段值+url字段值拼接成)，
        CacheObject resourceNeed2AuthObject = cache.get(CacheKey.RESOURCE, CacheKey.RESOURCE_NEED_TO_CHECK);
        List<String> resourceNeed2Auth = (List<String>) resourceNeed2AuthObject.getValue();

        if (resourceNeed2Auth == null) {
            // 如果缓存中没有相应数据，则通过 Feign 调用权限服务获取数据并放入缓存中
            resourceNeed2Auth = resourceApi.list().getData();
            if (!CollectionUtils.isEmpty(resourceNeed2Auth)) {
                // 放入缓存中
                cache.set(CacheKey.RESOURCE, CacheKey.RESOURCE_NEED_TO_CHECK, resourceNeed2Auth);
            }
        }

        // 第4步：判断这些资源是否包含当前请求的权限标识符，如果不包含当前请求的权限标识符，则返回未经授权错误提示
        boolean resourceNoneMatch = resourceNeed2Auth.stream().noneMatch(permission::startsWith);
        if (resourceNoneMatch) {
            // 当前请求是一个未知请求，直接返回未授权异常信息
            errorResponse(ExceptionCode.UNAUTHORIZED.getMsg(), ExceptionCode.UNAUTHORIZED.getCode(), 200);
            return null;
        }

        // 第5步：如果包含当前的权限标识符，则从 zuul 上下文 header 中取出用户 id，根据用户 id 取出缓存中的用户拥有的权限，判断用户拥有的权限是否包含当前请求的权限标识符
        String userId = RequestContext.getCurrentContext().getZuulRequestHeaders().get(BaseContextConstants.JWT_KEY_USER_ID);
        List<String> visibleResourceList = (List<String>) cache.get(CacheKey.USER_RESOURCE, userId).getValue();

        if (visibleResourceList == null) {
            // 如果缓存中不存在，则通过 Feign 远程调用权限服务获取数据并放入缓存
            ResourceQueryDTO resourceQueryDTO = ResourceQueryDTO.builder().userId(new Long(userId)).build();
            List<Resource> userResourceList = resourceApi.visible(resourceQueryDTO).getData();

            if (!CollectionUtils.isEmpty(userResourceList)) {
                visibleResourceList = userResourceList.stream()
                        .map(resource -> resource.getMethod() + resource.getUrl())
                        .collect(Collectors.toList());
                // 将当前用户拥有的权限载入缓存
                cache.set(CacheKey.USER_RESOURCE, userId, visibleResourceList);
            }
        }

        if (CollectionUtils.isEmpty(visibleResourceList) || visibleResourceList.stream().anyMatch(permission::startsWith)) {
            // 第6步：如果用户拥有的权限集合为空，或者不包含当前请求的权限标识符，则说明当前用户没有权限，返回未经授权错误提示
            log.warn("用户{}没有访问{}资源的权限", userId, method + permission);
            errorResponse(ExceptionCode.UNAUTHORIZED.getMsg(), ExceptionCode.UNAUTHORIZED.getCode(), 200);
        }

        // 第7步：如果用户拥有的权限包含当前请求的权限标识符则说明当前用户拥有权限，直接放行
        return null;
    }
}
