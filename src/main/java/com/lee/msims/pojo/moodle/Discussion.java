package com.lee.msims.pojo.moodle;

import lombok.Data;

@Data
public class Discussion {

    private int id;
    private String courseCode;
    private String sponsorId;
    private String sponsor;
    private String title;
    private String content;
    private String snapshot;
    private String date;

    public Discussion() {
    }

    public Discussion(String courseCode, String sponsorId, String sponsor, String title,
                      String content, String snapshot, String date) {
        this.courseCode = courseCode;
        this.sponsorId = sponsorId;
        this.sponsor = sponsor;
        this.title = title;
        this.content = content;
        this.snapshot = snapshot;
        this.date = date;
    }
}
