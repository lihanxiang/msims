package com.lee.msims.pojo.moodle;

import lombok.Data;

@Data
public class BulletinBoardMessage {

    private int id;
    private int boardId;
    private String content;
    private String date;

    public BulletinBoardMessage(int boardId, String content, String date) {
        this.boardId = boardId;
        this.content = content;
        this.date = date;
    }
}
