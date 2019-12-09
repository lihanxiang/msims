package com.lee.msims.service.moodle;

import com.lee.msims.pojo.moodle.BulletinBoard;
import com.lee.msims.pojo.moodle.BulletinBoardMessage;

import java.util.List;

public interface BulletinBoardService {

    // Insert
    void postMessageOnBoard(BulletinBoardMessage message);

    // Update
    void editMessage(BulletinBoardMessage message);

    // Select
    List<BulletinBoardMessage> getAllMessagesOnBoard(String courseCode);

    // Delete
    void deleteMessageOnBoard(int id);
}
