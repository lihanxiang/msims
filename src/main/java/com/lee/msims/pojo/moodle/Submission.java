package com.lee.msims.pojo.moodle;

import lombok.Data;

@Data
public class Submission {

    private int id;
    private int assignmentId;
    private String userId;
    private String fileId;
    private String author;
    private String comment;
    private String date;
    private int isGraded;

    public Submission(int assignmentId, String userId, String fileId, String author,
                      String comment, int isGraded, String date) {
        this.assignmentId = assignmentId;
        this.userId = userId;
        this.fileId = fileId;
        this.author = author;
        this.comment = comment;
        this.isGraded = isGraded;
        this.date = date;
    }
}
