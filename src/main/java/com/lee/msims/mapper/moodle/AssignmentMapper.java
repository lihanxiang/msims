package com.lee.msims.mapper.moodle;

import com.lee.msims.pojo.moodle.Assignment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AssignmentMapper {

    // Insert
    @Insert("INSERT INTO assignment (courseCode, fileId) VALUES (#{courseCode}, #{fileId})")
    void createAssignment(Assignment assignment);

    // Update
    @Update("UPDATE assignment SET fileId = #{fileId} WHERE id = #{id}")
    void editAssignmentInfo(Assignment assignment);

    // Select
    @Select("SELECT * FROM assignment WHERE id = #{id}")
    Assignment getAssignmentById(int id);

    @Select("SELECT * FROM assignment WHERE courseCode = #{courseCode}")
    List<Assignment> getAssignmentsOfCourse(String courseCode);

    @Select("SELECT assignmentId FROM student_assignment WHERE studentId = #{studentId}")
    List<String> getAssignmentsByStudentId(int studentId);

    @Select("SELECT studentId FROM student_assignment WHERE assignmentId = #{assignmentId}")
    List<String> getStudentsWhoAreSubmitted(int assignmentId);

    @Select("SELECT COUNT(*) FROM student_assignment WHERE studentId = #{studentId} AND assignmentId = #{assignmentId}")
    int checkIfSubmitted(@Param("studentId") int studentId, @Param("assignmentId") int assignmentId);

    // Delete
    @Delete("DELETE FROM assignment WHERE id = #{id}")
    void deleteAssignment(int id);
}
