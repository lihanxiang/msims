package com.lee.msims.mapper.GPA;

import com.lee.msims.pojo.coes.GPA;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GPAMapper {

    // Insert
    @Insert("INSERT INTO gpa (courseCode, courseName, userId, credit, grade)" +
            "VALUES (#{courseCode}, #{courseName}, #{userId}, #{credit}, #{grade})")
    void evaluateGPA(GPA gpa);

    // Update
    @Update("UPDATE gpa SET grade = #{grade} WHERE id = #{id}")
    void editGPA(GPA gpa);

    // Select
    @Select("SELECT * FROM gpa WHERE courseCode = #{courseCode} AND userId = #{userId}")
    GPA getGradeByCodeAndUserId(@Param("courseCode") String courseCode, @Param("userId") String userId);

    @Select("SELECT * FROM gpa WHERE courseCode = #{courseCode}")
    List<GPA> getGradeByCode(String courseCode);

    @Select("SELECT * FROM gpa WHERE userId = #{userId}")
    List<GPA> getGradeByUserId(String userId);

}
