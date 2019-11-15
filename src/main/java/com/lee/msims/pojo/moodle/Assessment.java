package com.lee.msims.pojo.moodle;

import lombok.Data;

@Data
public class Assessment {
    private int id;
    private int assignmentId;
    private int submissionId;
    private double score;
    private String comment;
    private String date;

    public Assessment(int assignmentId, int submissionId, double score, String comment, String date) {
        this.assignmentId = assignmentId;
        this.submissionId = submissionId;
        this.score = score;
        this.comment = comment;
        this.date = date;
    }
}
