package com.lee.msims.mapper.moodle;

import com.lee.msims.pojo.moodle.Submission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SubmissionMapper {

    // Insert
    @Insert("INSERT INTO submission (id, assignmentId, userId, fileId, author, comment, isGraded) " +
            "VALUES (#{id}, #{assignmentId}, #{userId}, #{fileId}, #{author}, #{comment}, #{isGraded})")
    void createSubmission(Submission submission);

    // Update
    
    void editSubmission(Submission submission);
}
