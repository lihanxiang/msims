package com.lee.msims.pojo.coes;

import lombok.Data;

@Data
public class GPA {
    private int id;
    private String courseCode;
    private String courseName;
    private String userId;
    private double credit;
    private String grade;

    public GPA(String courseCode, String courseName, String userId, double credit, String grade) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.userId = userId;
        this.credit = credit;
        this.grade = grade;
    }
}
