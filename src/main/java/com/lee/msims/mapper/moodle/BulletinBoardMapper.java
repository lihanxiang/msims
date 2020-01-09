package com.lee.msims.mapper.moodle;

import com.lee.msims.pojo.moodle.BulletinBoard;
import com.lee.msims.pojo.moodle.BulletinBoardMessage;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BulletinBoardMapper {

    // Insert
    @Insert("INSERT INTO bulletin_board_message (courseCode, content, date) VALUES (#{courseCode}, #{content}, #{date})")
    void postMessageOnBoard(BulletinBoardMessage message);

    // Update
    @Update("Update bulletin_board_message SET content = #{content}, date = #{date} WHERE id = #{id}")
    void editMessage(BulletinBoardMessage message);

    // Select
    @Select("SELECT * FROM bulletin_board_message WHERE courseCode = #{courseCode} ORDER BY date DESC")
    List<BulletinBoardMessage> getAllMessagesOnBoard(String courseCode);

    // Delete
    @Delete("Delete FROM bulletin_board_message WHERE id = #{id}")
    void deleteMessageOnBoard(int id);
}
