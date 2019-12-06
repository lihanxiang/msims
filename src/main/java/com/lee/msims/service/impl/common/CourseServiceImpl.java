package com.lee.msims.service.impl.common;

import com.lee.msims.mapper.common.CourseMapper;
import com.lee.msims.pojo.common.Course;
import com.lee.msims.service.common.CourseService;
import com.lee.msims.service.moodle.BulletinBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;
    private final BulletinBoardService bulletinBoardService;

    public CourseServiceImpl(CourseMapper courseMapper, BulletinBoardService bulletinBoardService) {
        this.courseMapper = courseMapper;
        this.bulletinBoardService = bulletinBoardService;
    }

    @Override
    public void addCourse(Course course) {
        courseMapper.addCourse(course);
        bulletinBoardService.createBoard(course.getCourseCode());
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
    public void deleteStudentOfCourse(String userId, String courseCode) {
        courseMapper.deleteStudentOfCourse(userId, courseCode);
    }

    @Override
    public void deleteCourse(String courseCode) {
        courseMapper.deleteCourse(courseCode);
    }
}
