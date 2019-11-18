package com.lee.msims.mapper.common;

import com.lee.msims.pojo.common.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    // Insert
    @Insert("INSERT INTO user (userId, username, password, gender, faculty, phone, email, roles, salt)" +
            "VALUES(#{userId}, #{username}, #{password}, #{gender}, #{faculty}, #{phone}, #{email}, #{roles}, #{salt})")
    void addUser(User user);

    // Update
    @Update("UPDATE user SET phone = #{phone}, email = #{email}")
    void updateUserInfo(User user);

    // Select
    @Select("SELECT * FROM user WHERE id = #{id}")
    User getUserById(int id);

    @Select("SELECT * FROM user WHERE userId = #{userId}")
    User getUserByUserId(@Param("userId") String userId);

    @Select("SELECT * FROM user WHERE username = #{username}")
    User getUserByUsername(String username);

    // Delete
    @Delete("DELETE FROM user WHERE id = #{id}")
    void deleteUser(User user);
}
