package com.moon.pinda.authority.biz.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.pinda.authority.dto.auth.ResourceQueryDTO;
import com.moon.pinda.authority.entity.auth.Resource;

import java.util.List;

/**
 * 资源业务接口
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-26 15:16
 * @description
 */
public interface ResourceService extends IService<Resource> {

    /**
     * 查询资源权限
     *
     * @param resource
     * @return
     */
    List<Resource> findVisibleResource(ResourceQueryDTO resource);

    /**
     * 根据菜单id删除资源
     */
    void removeByMenuId(List<Long> menuIds);

    /**
     * 根据资源id 查询菜单id
     */
    List<Long> findMenuIdByResourceId(List<Long> resourceIdList);
}
