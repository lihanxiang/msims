package com.lee.msims.pojo.moodle;

import lombok.Data;

@Data
public class Component {

    private int id;
    private String courseCode;
    private String type;        //Course Outline, Assignment...
    private String time;

    public Component() {}

    public Component(String courseCode, String type, String time) {
        this.courseCode = courseCode;
        this.type = type;
        this.time = time;
    }
}
