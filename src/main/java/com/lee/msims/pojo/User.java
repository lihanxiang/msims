package com.lee.msims.pojo;

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
    private String role;
    private String salt;

}
