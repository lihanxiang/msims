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
    private String content;
    private int userId;
    private String date;
}
