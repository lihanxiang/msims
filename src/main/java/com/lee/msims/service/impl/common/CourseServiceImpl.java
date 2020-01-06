package com.lee.msims.service.impl.common;

import com.lee.msims.mapper.common.CourseMapper;
import com.lee.msims.pojo.common.Course;
import com.lee.msims.service.common.CourseService;
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
    public List<String> getCourseListByStudent(String studentId) {
        return courseMapper.getCourseListByStudent(studentId);
    }

    @Override
    public List<String> getStudentsOfCourse(String courseCode) {
        return courseMapper.getStudentsOfCourse(courseCode);
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
