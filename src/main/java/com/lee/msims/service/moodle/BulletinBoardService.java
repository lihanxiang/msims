package com.lee.msims.service.moodle;

import com.lee.msims.pojo.moodle.BulletinBoard;
import com.lee.msims.pojo.moodle.BulletinBoardMessage;

import java.util.List;

public interface BulletinBoardService {

    // Insert
    void createBoard(String courseCode);

    void postMessageOnBoard(BulletinBoardMessage message);

    // Update
    void editMessage(BulletinBoardMessage message);

    // Select
    BulletinBoard getBoard(String courseCode);

    List<BulletinBoardMessage> getAllMessagesOnBoard(int boardId);

    // Delete
    void deleteMessageOnBoard(int id);

    void deleteBoard(int id);
}
