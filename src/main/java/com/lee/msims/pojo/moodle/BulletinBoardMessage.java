package com.lee.msims.pojo.moodle;

import lombok.Data;

@Data
public class BulletinBoardMessage {

    private int id;
    private String courseCode;
    private String content;
    private String date;

    public BulletinBoardMessage(){}

    public BulletinBoardMessage(String courseCode, String content, String date) {
        this.courseCode = courseCode;
        this.content = content;
        this.date = date;
    }
}
