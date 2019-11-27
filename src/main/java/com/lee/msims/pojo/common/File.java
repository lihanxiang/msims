package com.lee.msims.pojo.common;

import lombok.Data;

@Data
public class File {

    private int id;
    private String faculty;
    private String fileId;  //faculty_file_id
    private String name;
    private String path;
    private String time;

    public File(String faculty, String fileId, String name, String path, String time) {
        this.faculty = faculty;
        this.fileId = fileId;
        this.name = name;
        this.path = path;
        this.time = time;
    }
}
