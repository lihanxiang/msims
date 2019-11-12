package com.lee.msims.pojo.moodle;


import lombok.Data;

@Data
public class Assignment {

    private int id;
    private int componentId;
    private String description;
    private String submissionStatus;
    private String gradingStatus;
    private String dueStatus;
    private String lastModified;
}
