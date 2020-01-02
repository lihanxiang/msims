package com.lee.msims.service.moodle;

import com.lee.msims.pojo.moodle.Comment;
import java.util.List;

public interface CommentService {

    //Insert
    void createComment(Comment comment);

    void replyComment(Comment comment);

    //Update
    void editComment(Comment comment);

    //Select
    List<Comment> getAllCommentsOfDiscussion(int discussionId);

    List<Comment> getAllRepliesOfComment(int discussionId, int pid);

    //Delete
    void deleteComment(int id);
}
