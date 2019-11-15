package com.lee.msims.mapper.moodle;

import com.lee.msims.pojo.moodle.Assessment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AssessmentMapper {

    // Insert
    @Insert("INSERT INTO assessment (assignmentId, submissionId, score, comment, date) " +
            "VALUES (#{assignmentId}, #{submissionId}, #{score}, #{comment}, #{date})")
    void createAssessment(Assessment assessment);

    // Update
    @Update("UPDATE assessment SET score = #{score} AND comment = #{comment} AND date = #{date}")
    void editAssessment(Assessment assessment);

    // Select
    @Select("SELECT * FROM assessment WHERE id = #{id}")
    Assessment getAssessmentById(int id);

    @Select("SELECT * FROM assessment WHERE assignmentId = #{assignmentId}")
    List<Assessment> getAssessmentsInAssignment(int assignmentId);

    @Select("SELECT * FROM assessment WHERE score >= min AND score <= max")
    List<Assessment> getAssessmentByScore(@Param("min")double min, @Param("max")double max);

    // Delete
    @Delete("DELETE FROM assessment WHERE id = #{id}")
    void deleteAssessment(int id);
}
