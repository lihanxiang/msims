package com.lee.msims.pojo.moodle;

import lombok.Data;

@Data
public class CourseComponentFileSet {

    private int id;
    private int componentId;
    private String fileIdSet;
    private int size;
}
