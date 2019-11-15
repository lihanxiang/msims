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

    public Comment(String courseCode, int pid, int commenterId, int respondentId,
                   String content, String type, String date) {
        this.courseCode = courseCode;
        this.pid = pid;
        this.commenterId = commenterId;
        this.respondentId = respondentId;
        this.content = content;
        this.type = type;
        this.date = date;
    }
}
