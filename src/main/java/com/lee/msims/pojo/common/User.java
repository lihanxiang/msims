package com.lee.msims.pojo.common;

import lombok.Data;

@Data
public class User {

    private int id;
    private String userId;
    private String username;
    private String password;
    private String gender;
    private String faculty;
    private String phone;
    private String email;
    private String roles;
    private String salt;

    public User() {
    }

    public User(String userId, String username, String password, String gender, String faculty,
                String phone, String email, String roles) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.faculty = faculty;
        this.phone = phone;
        this.email = email;
        this.roles = roles;
    }
}
