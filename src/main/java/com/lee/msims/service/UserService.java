package com.lee.msims.service;

import com.lee.msims.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User getByName(String username);
}
