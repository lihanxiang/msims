package com.lee.msims.mapper.moodle;

import com.lee.msims.pojo.moodle.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    //Insert
    @Insert("INSERT INTO comment (courseCode, discussionId, pid, commenter, commenterId, respondent, respondentId, content, date)" +
            "VALUES (#{courseCode}, #{discussionId}, 0, #{commenter}, #{commenterId}, 'null' , 0, #{content}, #{date})")
    void createComment(Comment comment);

    @Insert("INSERT INTO comment (courseCode, discussionId, pid, commenter, commenterId, respondent, respondentId, content, date)" +
            "VALUES (#{courseCode}, #{discussionId}, #{pid}, #{commenter}, #{commenterId}, #{respondent} , #{respondentId}, #{content}, #{date})")
    void replyComment(Comment comment);

    //Update
    @Update("Update comment SET content = #{content}, date = #{date} " +
            "WHERE courseCode = #{courseCode} AND commenterId = #{commenterId}")
    void editComment(Comment comment);

    //Select
    @Select("SELECT * FROM comment WHERE discussionId = #{discussionId} AND pid = 0 ORDER BY date")
    List<Comment> getAllCommentsOfDiscussion(String discussionId);

    @Select("SELECT * FROM comment WHERE discussionId = #{discussionId} AND pid = #{pid} ORDER BY date")
    List<Comment> getAllRepliesOfComment(@Param("discussionId") String discussionId, @Param("pid") int pid);

    //Delete
    @Delete("DELETE FROM comment WHERE id = #{id}")
    void deleteComment(int id);
}
