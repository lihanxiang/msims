package com.lee.msims.pojo.moodle;


import lombok.Data;

@Data
public class Assignment {

    private int id;
    private String courseCode;
    private String fileId;

    public Assignment(){}

    public Assignment(String courseCode, String fileId) {
        this.courseCode = courseCode;
        this.fileId = fileId;
    }
}
