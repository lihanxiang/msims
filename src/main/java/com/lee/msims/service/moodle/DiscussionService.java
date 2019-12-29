package com.lee.msims.service.moodle;

import com.lee.msims.pojo.moodle.Comment;
import com.lee.msims.pojo.moodle.Discussion;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DiscussionService {

    // Insert
    void createDiscussion(Discussion discussion);

    // Update
    void editDiscussion(Discussion discussion);

    // Select
    List<Discussion> getAllDiscussionOfCourse(String courseCode);

    List<Discussion> getLatestFiveDiscussion(String courseCode);

    List<Comment> getAllCommentsOfDiscussion(int discussionId);

    // Delete
    void deleteDiscussion(int id);
}
