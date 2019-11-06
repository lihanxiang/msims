package com.lee.msims.pojo.moodle;

import lombok.Data;

@Data
public class Component {

    private int id;
    private String courseCode;
    private String type;        //Course Outline, Assignment...
}
