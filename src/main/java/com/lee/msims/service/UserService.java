package com.lee.msims.service;

import com.lee.msims.pojo.common.User;

import java.util.Set;

public interface UserService {

    void addUser(User user);

    // Update
    void updateUserInfo(User user);

    // Select
    User getUserById(int id);

    User getUserByUserId(String userId);

    User getUserByUsername(String username);

    Set<String> getRoles(String userId);

    // Delete
    void deleteUser(User user);
}
