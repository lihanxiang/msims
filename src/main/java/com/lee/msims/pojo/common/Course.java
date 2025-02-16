package com.lee.msims.pojo.common;

import lombok.Data;

@Data
public class Course {

    private int id;
    private String courseCode;
    private String faculty;
    private String courseName;
    private String credit;
    private String teacher;
    private String duration;    //9.4-12.17
    private String time;        //Monday@11:00-12:45,Tuesday@9:00-10:45
    private String description;

    public Course(String courseCode, String faculty, String courseName, String credit,
                  String teacher, String duration, String time, String description) {
        this.courseCode = courseCode;
        this.faculty = faculty;
        this.courseName = courseName;
        this.credit = credit;
        this.teacher = teacher;
        this.duration = duration;
        this.time = time;
        this.description = description;
    }
}
