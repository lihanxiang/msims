package com.lee.msims.mapper.common;

import com.lee.msims.pojo.common.Course;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CourseMapper {

    // Insert
    @Insert("INSERT INTO course (courseCode, faculty, courseName, credit, teacher, duration, time, description" +
            "VALUES (#{courseCode}, #{faculty}, #{name}, #{credit}, #[teacher}, #{duration}, #{time}, #{description})")
    void addCourse(Course course);

    @Insert("INSERT INTO student_course (userId, courseCode) VALUES (#{userId}, #{courseCode})")
    void addStudentToCourse(@Param("userId")String userId, @Param("courseCode")String courseCode);

    // Update
    @Update("UPDATE course SET courseCode = #{courseCode}, faculty = #{faculty}, " +
            "courseName = #{courseName}, credit = #{credit}, teacher = #{teacher}, " +
            "duration = #{duration},time = #{time}, description = #{description}")
    void updateCourseInfo(Course course);

    // Select
    @Select("SELECT * FROM course WHERE courseCode = #{courseCode}")
    Course getCourseInfoByCourseCode(String courseCode);

    @Select("SELECT * FROM course WHERE faculty = #{faculty}")
    List<Course> getCourseListByFaculty(String faculty);

    @Select("SELECT * FROM course WHERE teacher = #{teacher}")
    List<Course> getCourseListByTeacher(String teacher);

    @Select("SELECT courseCode FROM student_course WHERE studentId = #{studentId}")
    List<String> getCourseListByStudent(String studentId);

    // Delete
    @Delete("DELETE FROM student_course WHERE userId = #{userId} AND courseCode = #{courseCode}")
    void deleteStudentOfCourse(@Param("userId")String userId, @Param("courseCode")String courseCode);

    @Delete("DELETE FROM course WHERE courseCode = #{courseCode}")
    void deleteCourse(String courseCode);
}
