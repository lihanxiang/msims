package com.lee.msims.service;

import com.lee.msims.pojo.common.User;
import org.springframework.stereotype.Service;

public interface UserService {

    void addUser(User user);

    // Update
    void updateUserInfo(User user);

    // Select
    User getUserById(int id);

    User getUserByUserId(String userId);

    User getUserByUsername(String username);

    // Delete
    void deleteUser(User user);
}
