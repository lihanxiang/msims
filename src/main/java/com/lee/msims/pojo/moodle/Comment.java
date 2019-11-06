package com.lee.msims.pojo.moodle;

import lombok.Data;

/**
 * Comments in the discussion board
 */

@Data
public class Comment {

    private int id;
    private String courseCode;
    private int pid;
    private int commenterId;
    private int respondentId;
    private String content;
    private String type;
    private String date;
}
