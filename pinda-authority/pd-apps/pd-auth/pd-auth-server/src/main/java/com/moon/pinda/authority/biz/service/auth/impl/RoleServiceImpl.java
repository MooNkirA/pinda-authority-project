package com.moon.pinda.authority.biz.service.auth.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.pinda.authority.biz.dao.auth.RoleMapper;
import com.moon.pinda.authority.biz.service.auth.RoleAuthorityService;
import com.moon.pinda.authority.biz.service.auth.RoleOrgService;
import com.moon.pinda.authority.biz.service.auth.RoleService;
import com.moon.pinda.authority.biz.service.auth.UserRoleService;
import com.moon.pinda.authority.biz.service.auth.UserService;
import com.moon.pinda.authority.dto.auth.RoleSaveDTO;
import com.moon.pinda.authority.dto.auth.RoleUpdateDTO;
import com.moon.pinda.authority.entity.auth.Role;
import com.moon.pinda.authority.entity.auth.RoleAuthority;
import com.moon.pinda.authority.entity.auth.RoleOrg;
import com.moon.pinda.authority.entity.auth.User;
import com.moon.pinda.authority.entity.auth.UserRole;
import com.moon.pinda.base.id.CodeGenerate;
import com.moon.pinda.common.constant.CacheKey;
import com.moon.pinda.database.mybatis.conditions.Wraps;
import com.moon.pinda.dozer.DozerUtils;
import com.moon.pinda.utils.StrHelper;
import lombok.extern.slf4j.Slf4j;
import net.oschina.j2cache.CacheChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务实现类
 * 角色
 */
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private CacheChannel cache;
    @Autowired
    private DozerUtils dozer;
    @Autowired
    private RoleOrgService roleOrgService;
    @Autowired
    private RoleAuthorityService roleAuthorityService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserService userService;
    @Autowired
    private CodeGenerate codeGenerate;

    @Override
    public boolean removeById(List<Long> ids) {
        if (ids.isEmpty()) {
            return true;
        }
        ids.forEach(roleId -> {
            List<User> userList = userService.findUserByRoleId(roleId, null);
            if (userList != null && userList.size() > 0) {
                userList.forEach(user -> {
                    cache.evict(CacheKey.USER_RESOURCE, user.getId().toString());
                });
            }
        });

        //删除主表pd_auth_role数据
        super.removeByIds(ids);
        //删除pd_auth_role_org关系表数据
        roleOrgService.remove(Wraps.<RoleOrg>lbQ().in(RoleOrg::getRoleId, ids));
        //删除pd_auth_role_authority关系表数据
        roleAuthorityService.remove(Wraps.<RoleAuthority>lbQ().in(RoleAuthority::getRoleId, ids));
        //删除pd_auth_user_role关系表数据
        userRoleService.remove(Wraps.<UserRole>lbQ().in(UserRole::getRoleId, ids));

        return true;
    }

    @Override
    public List<Role> findRoleByUserId(Long userId) {
        return baseMapper.findRoleByUserId(userId);
    }

    /**
     * 1，保存角色
     * 2，保存 与组织的关系
     */
    @Override
    public void saveRole(RoleSaveDTO data, Long userId) {
        Role role = dozer.map(data, Role.class);
        role.setCode(StrHelper.getOrDef(data.getCode(), codeGenerate.next()));
        role.setReadonly(false);
        super.save(role);
        saveRoleOrg(userId, role, data.getOrgList());
    }

    @Override
    public void updateRole(RoleUpdateDTO data, Long userId) {
        Role role = dozer.map(data, Role.class);
        super.updateById(role);

        roleOrgService.remove(Wraps.<RoleOrg>lbQ().eq(RoleOrg::getRoleId, data.getId()));
        saveRoleOrg(userId, role, data.getOrgList());
    }

    private void saveRoleOrg(Long userId, Role role, List<Long> orgList) {
        if (orgList != null && !orgList.isEmpty()) {
            List<RoleOrg> list = orgList.stream().map((orgId) ->
                    RoleOrg.builder()
                            .orgId(orgId).roleId(role.getId())
                            .build()
            ).collect(Collectors.toList());
            roleOrgService.saveBatch(list);
        }
    }

    @Override
    public List<Long> findUserIdByCode(String[] codes) {
        return baseMapper.findUserIdByCode(codes);
    }

    @Override
    public Boolean check(String code) {
        return super.count(Wraps.<Role>lbQ().eq(Role::getCode, code)) > 0;
    }
}
