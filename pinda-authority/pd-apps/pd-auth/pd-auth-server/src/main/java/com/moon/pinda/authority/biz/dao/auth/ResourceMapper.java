package com.moon.pinda.authority.biz.dao.auth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moon.pinda.authority.dto.auth.ResourceQueryDTO;
import com.moon.pinda.authority.entity.auth.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资源 Mapper 接口
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-26 14:18
 * @description
 */
@Repository
public interface ResourceMapper extends BaseMapper<Resource> {

    /**
     * 查询指定用户拥有的资源权限
     *
     * @param resource
     * @return
     */
    List<Resource> findVisibleResource(ResourceQueryDTO resource);

    List<Long> findMenuIdByResourceId(@Param("resourceIdList") List<Long> resourceIdList);
}
