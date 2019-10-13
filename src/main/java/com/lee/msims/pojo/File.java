package com.lee.msims.pojo;

import lombok.Data;

@Data
public class File {

    private int id;
    private String fileId;  //faculty_file_id
    private String faculty;
    private String name;
    private String path;
}
