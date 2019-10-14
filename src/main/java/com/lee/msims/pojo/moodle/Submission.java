package com.lee.msims.pojo.moodle;

import lombok.Data;

@Data
public class Submission {

    private int id;
    private String title;
    private String description;
    private String submissionStatus;
    private String gradingStatus;
    private String dueStatus;
    private String lastModified;
    private String fileId;
    private String submissionFileId;
    private String comment;
}
