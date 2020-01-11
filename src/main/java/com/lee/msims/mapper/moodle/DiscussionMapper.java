package com.lee.msims.mapper.moodle;

import com.lee.msims.pojo.moodle.Comment;
import com.lee.msims.pojo.moodle.Discussion;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DiscussionMapper {

    // Insert
    @Insert("INSERT INTO discussion (courseCode, sponsor, sponsorId, title, content, snapshot, date)" +
            "VALUES (#{courseCode}, #{sponsor}, #{sponsorId}, #{title}, #{content}, #{snapshot}, #{date})")
    void createDiscussion(Discussion discussion);

    // Update
    @Update("UPDATE discussion SET title = #{title}, content = #{content}, " +
            "snapshot = #{snapshot} WHERE id = #{id}")
    void editDiscussion(Discussion discussion);

    // Select
    @Select("SELECT * FROM discussion WHERE courseCode = #{courseCode} ORDER BY date DESC")
    List<Discussion> getAllDiscussionOfCourse(String courseCode);

    @Select("SELECT * FROM discussion WHERE courseCode = #{courseCode} ORDER BY date DESC LIMIT 5")
    List<Discussion> getLatestFiveDiscussion(String courseCode);

    @Select("SELECT * FROM comment WHERE discussionId = #{discussionId}")
    List<Comment> getAllCommentsOfDiscussion(int discussionId);

    // Delete
    @Delete("DELETE FROM discussion WHERE id = #{id}")
    void deleteDiscussion(int id);
}
