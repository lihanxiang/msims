package com.lee.msims.pojo.moodle;


import lombok.Data;

@Data
public class Assignment {

    private int id;
    private int componentId;
    private String title;
    private String description;
    private String dueStatus;
}
