package com.lee.msims.mapper;

import com.lee.msims.pojo.moodle.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    //Insert
    @Insert("INSERT INTO comment (courseCode, pid, commenterId, respondentId, content, type, date)" +
            "VALUES (#{courseCode}, 0, #{commenterId}, #{respondentId}, #{content}, 'comment', #{date})")
    void createComment(Comment comment);

    @Insert("INSERT INTO comment (courseCode, pid, commenterId, respondentId, content, type, date)" +
            "VALUES (#{courseCode}, #{pid}, #{commenterId}, #{respondentId}, #{content}, 'reply', #{date})")
    void replyComment(Comment comment);

    //Update
    @Update("Update comment SET content = #{content}, date = #{date} " +
            "WHERE courseCode = #{courseCode} AND commenterId = #{commenterId}")
    void editComment(Comment comment);

    //Select
    @Select("SELECT * FROM comment WHERE courseCode = #{courseCode}")
    List<Comment> getAllCommentsOfCourse(String courseCode);

    @Select("SELECT * FROM comment WHERE pid = #{pid}")
    List<Comment> getAllRepliesOfComment(int pid);

    //Delete
    @Delete("DELETE FROM comment WHERE id = #{id}")
    void deleteComment(int id);
}
