package com.lee.msims.service.impl.moodle;

import com.lee.msims.mapper.moodle.CommentMapper;
import com.lee.msims.pojo.moodle.Comment;
import com.lee.msims.service.moodle.CommentService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    public void createComment(Comment comment) {
        commentMapper.createComment(comment);
    }

    @Override
    public void replyComment(Comment comment) {
        commentMapper.replyComment(comment);
    }

    @Override
    public void editComment(Comment comment) {
        commentMapper.editComment(comment);
    }

    @Override
    public List<Comment> getAllCommentsOfCourse(String courseCode) {
        return commentMapper.getAllCommentsOfCourse(courseCode);
    }

    @Override
    public List<Comment> getAllRepliesOfComment(int pid) {
        return commentMapper.getAllRepliesOfComment(pid);
    }

    @Override
    public void deleteComment(int id) {
        commentMapper.deleteComment(id);
    }
}
