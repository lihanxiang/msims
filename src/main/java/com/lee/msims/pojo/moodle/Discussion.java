package com.lee.msims.pojo.moodle;

import lombok.Data;

@Data
public class Discussion {

    private int id;
    private String courseCode;
    private int pid;
    private String content;
    private int userId;
    private String date;
}
