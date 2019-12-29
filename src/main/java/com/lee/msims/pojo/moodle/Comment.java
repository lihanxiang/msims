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
    private String commenter;
    private int commenterId;
    private String respondent;
    private int respondentId;
    private String content;
    private String date;

    public Comment(){}

    public Comment(String courseCode, int discussionId, int pid, String commenter, int commenterId,
                   String respondent, int respondentId, String content, String date) {
        this.courseCode = courseCode;
        this.discussionId = discussionId;
        this.pid = pid;
        this.commenter = commenter;
        this.commenterId = commenterId;
        this.respondent = respondent;
        this.respondentId = respondentId;
        this.content = content;
        this.date = date;
    }
}
