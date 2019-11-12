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
    private int isGraded;
}
