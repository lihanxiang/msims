package com.lee.msims.pojo.moodle;


import lombok.Data;

@Data
public class Assignment {

    private int id;
    private int componentId;
    private String title;
    private String description;
    private String deadline;

    public Assignment(int componentId, String title, String description, String deadline) {
        this.componentId = componentId;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }
}
