package com.lee.msims.pojo.moodle;

import lombok.Data;

@Data
public class Submission {

    private int id;
    private int assignmentId;
    private int studentId;
    private String fileId;
    private String comment;
    private String date;
    private int isGraded;

    public Submission(){}

    public Submission(int assignmentId, int studentId, String fileId,
                      String comment, String date, int isGraded) {
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.fileId = fileId;
        this.comment = comment;
        this.date = date;
        this.isGraded = isGraded;
    }
}
