package com.lee.msims.pojo.portal;

import lombok.Data;

@Data
public class News {
    private int id;
    private String title;
    private String faculty;
    private String content;
    private String date;

    public News(String title, String faculty, String content, String date) {
        this.title = title;
        this.faculty = faculty;
        this.content = content;
        this.date = date;
    }
}
