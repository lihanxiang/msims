package com.lee.msims.mapper.moodle;

import com.lee.msims.pojo.moodle.Submission;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SubmissionMapper {

    // Insert
    @Insert("INSERT INTO submission (id, assignmentId, userId, fileId, author, comment, isGraded) " +
            "VALUES (#{id}, #{assignmentId}, #{userId}, #{fileId}, #{author}, #{comment}, #{isGraded})")
    void createSubmission(Submission submission);

    // Update
    @Update("UPDATE submission SET fileId = #{fileId} AND author = #{author} AND comment = #{comment}")
    void editSubmission(Submission submission);

    // Select
    @Select("SELECT * FROM submission WHERE id = #{id}")
    Submission getSubmissionById(int id);

    @Select("SELECT * FROM submission WHERE assignmentId = #{assignmentId}")
    List<Submission> getSubmissionInAssignment(int assignmentId);

    @Select("SELECT * FROM submission WHERE userId = #{userId}")
    List<Submission> getSubmissionByUserId(int userId);

    // Delete
    @Delete("DELETE FROM submission WHERE id = #{id}")
    void deleteSubmission(int id);
}
