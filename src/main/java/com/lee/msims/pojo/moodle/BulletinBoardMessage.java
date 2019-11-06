package com.lee.msims.pojo.moodle;

import lombok.Data;

@Data
public class BulletinBoardMessage {

    private int id;
    private int boardId;
    private String content;
    private String date;
}
