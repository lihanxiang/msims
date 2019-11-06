package com.lee.msims.service.impl;

import com.lee.msims.mapper.CourseMapper;
import com.lee.msims.pojo.common.Course;
import com.lee.msims.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;

    public CourseServiceImpl(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Override
    public void addCourse(Course course) {
        courseMapper.addCourse(course);
    }

    @Override
    public void addStudentToCourse(String userId, String courseCode) {
        courseMapper.addStudentToCourse(userId, courseCode);
    }

    @Override
    public void updateCourseInfo(Course course) {
        courseMapper.updateCourseInfo(course);
    }

    @Override
    public Course getCourseInfoByCourseCode(String courseCode) {
        return courseMapper.getCourseInfoByCourseCode(courseCode);
    }

    @Override
    public List<Course> getCourseListByFaculty(String faculty) {
        return courseMapper.getCourseListByFaculty(faculty);
    }

    @Override
    public List<Course> getCourseListByTeacher(String teacher) {
        return courseMapper.getCourseListByTeacher(teacher);
    }

    @Override
    public List<Course> getCourseListByStudent(String studentId) {
        return courseMapper.getCourseListByStudent(studentId);
    }

    @Override
    public void deleteStudentOfCourse(String userId, String courseCode) {
        courseMapper.deleteStudentOfCourse(userId, courseCode);
    }

    @Override
    public void deleteCourse(String courseCode) {
        courseMapper.deleteCourse(courseCode);
    }
}
