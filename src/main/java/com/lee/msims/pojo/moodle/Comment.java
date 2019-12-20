package com.lee.msims.pojo.moodle;

import lombok.Data;

/**
 * Comments in the discussion board
 */

@Data
public class Comment {

    private int id;
    private String courseCode;
    private int discussionId;
    private int pid;
    private int commenterId;
    private String commenter;
    private int respondentId;
    private String respondent;
    private String content;
    private String date;

    public Comment(){}

    public Comment(String courseCode, int discussionId, int pid, int commenterId, String commenter,
                   int respondentId, String respondent, String content, String date) {
        this.courseCode = courseCode;
        this.discussionId = discussionId;
        this.pid = pid;
        this.commenterId = commenterId;
        this.commenter = commenter;
        this.respondentId = respondentId;
        this.respondent = respondent;
        this.content = content;
        this.date = date;
    }
}
