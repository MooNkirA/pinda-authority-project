package com.moon.pinda.authority.biz.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.pinda.authority.entity.auth.RoleOrg;

import java.util.List;

/**
 * 业务接口
 * 角色组织关系
 */
public interface RoleOrgService extends IService<RoleOrg> {
    /**
     * 根据角色id查询
     */
    List<Long> listOrgByRoleId(Long id);
}
