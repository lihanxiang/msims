package com.lee.msims.pojo.moodle;


import lombok.Data;

@Data
public class Assignment {

    private int id;
    private int componentId;
    private String title;
    private String description;
    private String deadline;
    private String dueStatus;

    public Assignment(int componentId, String title, String description, String deadline, String dueStatus) {
        this.componentId = componentId;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.dueStatus = dueStatus;
    }
}
