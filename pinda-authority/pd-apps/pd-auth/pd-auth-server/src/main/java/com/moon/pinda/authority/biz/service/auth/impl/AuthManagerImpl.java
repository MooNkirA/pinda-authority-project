package com.moon.pinda.authority.biz.service.auth.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.moon.pinda.auth.server.utils.JwtTokenServerUtils;
import com.moon.pinda.auth.utils.JwtUserInfo;
import com.moon.pinda.auth.utils.Token;
import com.moon.pinda.authority.biz.service.auth.AuthManager;
import com.moon.pinda.authority.biz.service.auth.ResourceService;
import com.moon.pinda.authority.biz.service.auth.UserService;
import com.moon.pinda.authority.dto.auth.LoginDTO;
import com.moon.pinda.authority.dto.auth.ResourceQueryDTO;
import com.moon.pinda.authority.dto.auth.UserDTO;
import com.moon.pinda.authority.entity.auth.Resource;
import com.moon.pinda.authority.entity.auth.User;
import com.moon.pinda.common.constant.CacheKey;
import com.moon.pinda.dozer.DozerUtils;
import com.moon.pinda.exception.BizException;
import com.moon.pinda.exception.code.ExceptionCode;
import net.oschina.j2cache.CacheChannel;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 认证管理器实现
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-26 10:43
 * @description
 */
@Service
public class AuthManagerImpl implements AuthManager {

    @Autowired
    private UserService userService;
    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private JwtTokenServerUtils jwtTokenServerUtils;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private CacheChannel cache;

    /**
     * 账户认证
     *
     * @param account
     * @param password
     * @return
     */
    @Override
    public LoginDTO login(String account, String password) {
        // 校验账号、密码是否正确。注：只要通过校验，返回的用户对象都不为空
        User user = check(account, password);
        // 根据用户生成 Token
        Token token = generateUserToken(user);

        // 查询当前用户可以访问的资源权限
        ResourceQueryDTO resourceQueryDTO = ResourceQueryDTO.builder().userId(user.getId()).build();
        List<Resource> userResources = resourceService.findVisibleResource(resourceQueryDTO);

        // 定义返回前端页面的权限集合
        List<String> permissionList = Collections.emptyList();

        if (!CollectionUtils.isEmpty(userResources)) {
            // 将用户对应的权限列表转换成供前端页面使用的格式
            permissionList = userResources.stream().map(Resource::getCode).collect(Collectors.toList());

            // 将用户对应的权限列表转换成供后端网关使用的格式，并缓存
            List<String> gwResources = userResources.stream()
                    .map(resource -> resource.getMethod() + resource.getUrl())
                    .collect(Collectors.toList());
            // 缓存数据
            cache.set(CacheKey.USER_RESOURCE, user.getId().toString(), gwResources);
        }

        // 使用 dozer 工具将 DO 转 DTO，返回结果
        return LoginDTO.builder()
                .user(dozerUtils.map(user, UserDTO.class))
                .token(token)
                .permissionsList(permissionList)
                .build();
    }

    /**
     * 校验账户与密码，通过后用户对象
     *
     * @param account
     * @param password
     * @return
     */
    @Override
    public User check(String account, String password) {
        // 根据账号查询
        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getAccount, account));

        // 将前端提交的密码进行md5加密
        String md5Hex = DigestUtils.md5Hex(password);

        if (user == null || !user.getPassword().equals(md5Hex)) {
            // 如果用户为空，或者密码不一致，则认证失败
            throw BizException.validFail(ExceptionCode.JWT_USER_INVALID.getMsg());
        }
        // 认证成功，返回用户对象
        return user;
    }

    /**
     * 为用户生成对应的jwt令牌
     *
     * @param user 用户实例
     * @return
     */
    @Override
    public Token generateUserToken(User user) {
        // 创建 jwt 存储的内容
        JwtUserInfo jwtUserInfo = new JwtUserInfo();
        jwtUserInfo.setUserId(user.getId());
        jwtUserInfo.setAccount(user.getAccount());
        jwtUserInfo.setName(user.getName());
        jwtUserInfo.setOrgId(user.getOrgId());
        jwtUserInfo.setStationId(user.getStationId());
        // 生成 Token 并返回
        return jwtTokenServerUtils.generateUserToken(jwtUserInfo, null);
    }
}
