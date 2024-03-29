package com.moon.pinda.authority.biz.service.auth;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.pinda.authority.dto.auth.UserUpdatePasswordDTO;
import com.moon.pinda.authority.entity.auth.User;
import com.moon.pinda.database.mybatis.conditions.query.LbqWrapper;

import java.util.List;

/**
 * 用户账号业务接口
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-26 10:23
 * @description
 */
public interface UserService extends IService<User> {
    /**
     * 根据角色id 和 账号或名称 查询角色关联的用户
     * 注意，该接口只返回 id，账号，姓名，手机，性别
     *
     * @param roleId  角色id
     * @param keyword 账号或名称
     */
    List<User> findUserByRoleId(Long roleId, String keyword);

    /**
     * 修改输错密码的次数
     */
    void updatePasswordErrorNumById(Long id);

    /**
     * 根据账号查询用户
     */
    User getByAccount(String account);

    /**
     * 修改用户最后一次登录 时间
     */
    void updateLoginTime(String account);

    /**
     * 保存
     */
    User saveUser(User user);

    /**
     * 重置密码
     */
    boolean reset(List<Long> ids);

    /**
     * 修改
     */
    User updateUser(User user);

    /**
     * 删除
     */
    boolean remove(List<Long> ids);

    /**
     * 分页
     */
    IPage<User> findPage(IPage<User> page, LbqWrapper<User> wrapper);

    /**
     * 修改密码
     */
    Boolean updatePassword(UserUpdatePasswordDTO data);

    /**
     * 重置密码错误次数
     */
    int resetPassErrorNum(Long id);
}
