package com.lee.msims.pojo.moodle;

import lombok.Data;

@Data
public class CourseComponent {

    private int id;
    private String courseCode;
    private String componentType;        //Course Outline, Assignment...
    private String fileId;
}
