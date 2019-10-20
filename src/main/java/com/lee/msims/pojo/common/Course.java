package com.lee.msims.pojo.common;

import lombok.Data;

@Data
public class Course {

    private int id;
    private String courseCode;
    private String name;
    private double credit;
    private String teacher;
    private String duration;    //9.4-12.17
    private String time;        //Monday@11:00-12:45,Tuesday@9:00-10:45
    private String description;
}
