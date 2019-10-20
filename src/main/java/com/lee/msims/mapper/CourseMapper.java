package com.lee.msims.mapper;

import com.lee.msims.pojo.common.Course;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CourseMapper {

    // Insert
    @Insert("INSERT INTO course (courseCode, name, credit, teacher, duration, time, description" +
            "VALUES (#{courseCode}, #{name}, #{credit}, #[teacher}, #{duration}, #{time}, #{description})")
    void addCourse(Course course);

    @Insert("INSERT INTO student_course (userId, courseCode) VALUES (#{userId}, #{courseCode})")
    void addStudentToCourse(@Param("userId")String userId, @Param("courseCode")String courseCode);

    // Update

    void updateCourseInfo(Course course);

    // Select

    // Delete
    @Delete("DELETE FROM student_course WHERE userId = #{userId} AND courseCode = #{courseCode}")
    void deleteStudentToCourse(@Param("userId")String userId, @Param("courseCode")String courseCode);
}
