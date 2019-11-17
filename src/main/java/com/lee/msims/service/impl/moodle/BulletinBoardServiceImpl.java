package com.lee.msims.service.impl.moodle;

import com.lee.msims.mapper.moodle.BulletinBoardMapper;
import com.lee.msims.pojo.moodle.BulletinBoard;
import com.lee.msims.pojo.moodle.BulletinBoardMessage;
import com.lee.msims.service.moodle.BulletinBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BulletinBoardServiceImpl implements BulletinBoardService {

    private final BulletinBoardMapper bulletinBoardMapper;

    public BulletinBoardServiceImpl(BulletinBoardMapper bulletinBoardMapper) {
        this.bulletinBoardMapper = bulletinBoardMapper;
    }

    @Override
    public void createBoard(String courseCode) {
        bulletinBoardMapper.createBoard(courseCode);
    }

    @Override
    public void postMessageOnBoard(BulletinBoardMessage message) {
        bulletinBoardMapper.postMessageOnBoard(message);
    }

    @Override
    public void editMessage(BulletinBoardMessage message) {
        bulletinBoardMapper.editMessage(message);
    }

    @Override
    public BulletinBoard getBoard(String courseCode) {
        return bulletinBoardMapper.getBoard(courseCode);
    }

    @Override
    public List<BulletinBoardMessage> getAllMessagesOnBoard(int boardId) {
        return bulletinBoardMapper.getAllMessagesOnBoard(boardId);
    }

    @Override
    public void deleteMessageOnBoard(int id) {
        bulletinBoardMapper.deleteMessageOnBoard(id);
    }

    @Override
    public void deleteBoard(int id) {
        bulletinBoardMapper.deleteBoard(id);
    }
}
