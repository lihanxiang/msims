package com.lee.msims.pojo.moodle;

import lombok.Data;

@Data
public class Discussion {

    private int id;
    private String courseCode;
    private String sponsor;
    private int sponsorId;
    private String title;
    private String content;
    private String snapshot;
    private String date;

    public Discussion() {
    }

    public Discussion(String courseCode, String sponsor, int sponsorId, String title,
                      String content, String snapshot, String date) {
        this.courseCode = courseCode;
        this.sponsor = sponsor;
        this.sponsorId = sponsorId;
        this.title = title;
        this.content = content;
        this.snapshot = snapshot;
        this.date = date;
    }
}
