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
    @Insert("INSERT INTO bulletin_board (courseCode) VALUES (#{courseCode})")
    void createBoard(String courseCode);

    @Insert("INSERT INTO bulletin_board_message (boardId, content, date) VALUES (#{boardId}, #{content}, #{date})")
    void postMessageOnBoard(BulletinBoardMessage message);

    // Update
    @Update("Update bulletin_board_message SET content = #{content}, date = #{date} WHERE id = #{id}")
    void editMessage(BulletinBoardMessage message);

    // Select
    @Select("SELECT * FROM bulletin_board WHERE courseCode = #{courseCode}")
    BulletinBoard getBoard(String courseCode);

    @Select("SELECT * FROM bulletin_board_message WHERE boardId = #{boardId}")
    List<BulletinBoardMessage> getAllMessagesOnBoard(int boardId);

    // Delete
    @Delete("Delete FROM bulletin_board_message WHERE id = #{id}")
    void deleteMessageOnBoard(int id);

    @Delete("Delete FROM bulletin_board WHERE id = #{id}")
    void deleteBoard(int id);
}
