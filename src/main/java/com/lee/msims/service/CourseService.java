package com.lee.msims.service;

import com.lee.msims.pojo.common.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CourseService {

    // Insert
    void addCourse(Course course);

    void addStudentToCourse(@Param("userId")String userId, @Param("courseCode")String courseCode);

    // Update
    void updateCourseInfo(Course course);

    // Select
    Course getCourseInfoByCourseCode(String courseCode);

    List<Course> getCourseListByFaculty(String faculty);

    List<Course> getCourseListByTeacher(String teacher);

    List<Course> getCourseListByStudent(String studentId);

    // Delete
    void deleteStudentOfCourse(@Param("userId")String userId, @Param("courseCode")String courseCode);

    void deleteCourse(String courseCode);
}
