package com.lee.msims.mapper.moodle;

import com.lee.msims.pojo.moodle.Assignment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AssignmentMapper {

    // Insert
    @Insert("INSERT INTO assignment (componentId, title, description, dueStatus" +
            "VALUES (#{componentId}, #{title}, #{description}, #{dueStatus})")
    void createAssignment(Assignment assignment);

    // Update
    @Update("UPDATE assignment SET componentId = #{componentId} AND title = #{title} " +
            "AND description = #{description} AND dueStatus = #{dueStatus}")
    void editAssignmentInfo(Assignment assignment);

    // Select
    @Select("SELECT * FROM assignment WHERE id = #{id}")
    Assignment getAssignmentById(int id);

    @Select("SELECT * FROM assignment WHERE componentId = #{componentId}")
    List<Assignment> getAssignmentsInComponent(int componentId);

    // Delete
    @Delete("DELETE FROM assignment WHERE id = #{id}")
    void deleteAssignment(int id);
}
