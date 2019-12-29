package com.lee.msims.service.impl.moodle;

import com.lee.msims.mapper.moodle.DiscussionMapper;
import com.lee.msims.pojo.moodle.Comment;
import com.lee.msims.pojo.moodle.Discussion;
import com.lee.msims.service.moodle.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussionServiceImpl implements DiscussionService {

    @Autowired
    private DiscussionMapper discussionMapper;

    @Override
    public void createDiscussion(Discussion discussion) {
        discussionMapper.createDiscussion(discussion);
    }

    @Override
    public void editDiscussion(Discussion discussion) {
        discussionMapper.editDiscussion(discussion);
    }

    @Override
    public List<Discussion> getAllDiscussionOfCourse(String courseCode) {
        return discussionMapper.getAllDiscussionOfCourse(courseCode);
    }

    @Override
    public List<Discussion>  getLatestFiveDiscussion(String courseCode) {
        return discussionMapper.getLatestFiveDiscussion(courseCode);
    }

    @Override
    public List<Comment> getAllCommentsOfDiscussion(int discussionId) {
        return discussionMapper.getAllCommentsOfDiscussion(discussionId);
    }

    @Override
    public void deleteDiscussion(int id) {
        discussionMapper.deleteDiscussion(id);
    }
}
