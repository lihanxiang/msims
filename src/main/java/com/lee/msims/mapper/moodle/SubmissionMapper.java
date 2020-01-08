package com.lee.msims.mapper.moodle;

import com.lee.msims.pojo.moodle.Submission;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SubmissionMapper {

    // Insert
    @Insert("INSERT INTO submission (assignmentId, studentId, fileId, comment, date, isGraded) " +
            "VALUES (#{assignmentId}, #{studentId}, #{fileId}, #{comment}, #{date}, #{isGraded})")
    void createSubmission(Submission submission);

    // Update
    @Update("UPDATE submission SET fileId = #{fileId} AND comment = #{comment}")
    void editSubmission(Submission submission);

    // Select
    @Select("SELECT * FROM submission WHERE id = #{id}")
    Submission getSubmissionById(int id);

    @Select("SELECT * FROM submission WHERE assignmentId = #{assignmentId}")
    List<Submission> getSubmissionInAssignment(int assignmentId);

    @Select("SELECT * FROM submission WHERE studentId = #{studentId}")
    List<Submission> getSubmissionByStudentId(int studentId);

    // Delete
    @Delete("DELETE FROM submission WHERE id = #{id}")
    void deleteSubmission(int id);
}
