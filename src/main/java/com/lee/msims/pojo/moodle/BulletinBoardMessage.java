package com.lee.msims.pojo.moodle;

import lombok.Data;

@Data
public class BulletinBoardMessage {

    private int id;
    private int bulletinBoardId;
    private String content;
    private String date;
}
