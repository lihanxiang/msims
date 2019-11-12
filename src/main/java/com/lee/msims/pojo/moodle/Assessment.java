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
}
