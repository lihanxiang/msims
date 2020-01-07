package com.lee.msims.pojo.moodle;


import lombok.Data;

@Data
public class Assignment {

    private int id;
    private String courseCode;
    private String title;
    private String description;
    private String deadline;

    public Assignment(String courseCode, String title, String description, String deadline) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }
}
