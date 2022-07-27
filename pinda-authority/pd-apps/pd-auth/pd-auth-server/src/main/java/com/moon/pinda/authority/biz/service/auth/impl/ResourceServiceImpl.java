package com.moon.pinda.authority.biz.service.auth.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.pinda.authority.biz.dao.auth.ResourceMapper;
import com.moon.pinda.authority.biz.service.auth.ResourceService;
import com.moon.pinda.authority.dto.auth.ResourceQueryDTO;
import com.moon.pinda.authority.entity.auth.Resource;
import com.moon.pinda.base.id.CodeGenerate;
import com.moon.pinda.database.mybatis.conditions.Wraps;
import com.moon.pinda.exception.BizException;
import com.moon.pinda.utils.StrHelper;
import net.oschina.j2cache.CacheChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 资源业务接口实现
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-26 15:17
 * @description
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    @Autowired
    private CacheChannel cache;
    @Autowired
    private CodeGenerate codeGenerate;

    /**
     * 查询资源权限
     *
     * @param resource
     * @return
     */
    @Override
    public List<Resource> findVisibleResource(ResourceQueryDTO resource) {
        // 查询当前用户可访问的资源
        return baseMapper.findVisibleResource(resource);
    }

    @Override
    public void removeByMenuId(List<Long> menuIds) {
        List<Resource> resources = super.list(Wraps.<Resource>lbQ().in(Resource::getMenuId, menuIds));
        if (resources.isEmpty()) {
            return;
        }
        List<Long> idList = resources.stream().mapToLong(Resource::getId).boxed().collect(Collectors.toList());
        super.removeByIds(idList);
    }

    @Override
    public boolean save(Resource resource) {
        resource.setCode(StrHelper.getOrDef(resource.getCode(), codeGenerate.next()));
        if (super.count(Wraps.<Resource>lbQ().eq(Resource::getCode, resource.getCode())) > 0) {
            throw BizException.validFail("编码[%s]重复", resource.getCode());
        }
        super.save(resource);
        return true;
    }

    @Override
    public List<Long> findMenuIdByResourceId(List<Long> resourceIdList) {
        return baseMapper.findMenuIdByResourceId(resourceIdList);
    }
}
