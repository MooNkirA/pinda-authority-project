package com.moon.pinda.zuul.api;

import com.moon.pinda.authority.dto.auth.ResourceQueryDTO;
import com.moon.pinda.authority.entity.auth.Resource;
import com.moon.pinda.base.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 资源服务 feign 代理接口熔断处理类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-27 11:13
 * @description
 */
@Component
public class ResourceApiFallback implements ResourceApi {

    @Override
    public R<List> list() {
        return null;
    }

    @Override
    public R<List<Resource>> visible(ResourceQueryDTO resource) {
        return null;
    }

}
