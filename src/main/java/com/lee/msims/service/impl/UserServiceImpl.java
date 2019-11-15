package com.lee.msims.service.impl;

import com.lee.msims.mapper.common.UserMapper;
import com.lee.msims.pojo.common.User;
import com.lee.msims.service.UserService;
import com.lee.msims.util.Encryption;
import com.lee.msims.util.RoleConverter;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final Encryption encryption;
    private final RoleConverter roleConverter;

    public UserServiceImpl(UserMapper userMapper, Encryption encryption, RoleConverter roleConverter) {
        this.userMapper = userMapper;
        this.encryption = encryption;
        this.roleConverter = roleConverter;
    }

    @Override
    public void addUser(User user) {
        encryption.encryptPassword(user);
        userMapper.addUser(user);
    }

    @Override
    public void updateUserInfo(User user) {

    }

    @Override
    public User getUserById(int id) {
        return userMapper.getUserById(id);
    }

    @Override
    public User getUserByUserId(String userId) {
        return userMapper.getUserByUserId(userId);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public Set<String> getRoles(String userId) {
        return roleConverter.convertRolesIntoRoleSet(userMapper.getUserByUserId(userId).getRoles());
    }

    @Override
    public void deleteUser(User user) {

    }
}
