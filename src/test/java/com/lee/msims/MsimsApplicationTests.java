package com.lee.msims;

import com.lee.msims.pojo.common.File;
import com.lee.msims.pojo.common.User;
import com.lee.msims.pojo.moodle.Comment;
import com.lee.msims.pojo.moodle.Component;
import com.lee.msims.pojo.moodle.Discussion;
import com.lee.msims.service.common.FileService;
import com.lee.msims.service.common.UserService;
import com.lee.msims.service.moodle.CommentService;
import com.lee.msims.service.moodle.ComponentService;
import com.lee.msims.service.moodle.DiscussionService;
import com.lee.msims.util.DateFormatter;
import com.lee.msims.util.FileIDBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MsimsApplicationTests {

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private ComponentService componentService;
    @Autowired
    private DiscussionService discussionService;
    @Autowired
    private DateFormatter dateFormatter;
    @Autowired
    private CommentService commentService;

    @Test
    public void addUser(){
        /*User user = new User("teacherID", "Subrota","Subrota", "male",
                "FIT", "88973007", "skmondal@must.edu.mo", "teacher");
        userService.addUser(user);

        User user1 = new User("123", "123","123", "123",
                "123", "123", "123", "123");
        userService.addUser(user1);

        User admin = new User("admin", "admin","admin", "admin",
                "admin", "admin", "admin", "admin");
        userService.addUser(admin);*/
        User user = new User("teacher", "teacher","teacher", "male",
                "FIT", "teacher", "teacher", "teacher");
        userService.addUser(user);
    }

    @Test
    public void addDiscussion(){
        Discussion discussion1 = new Discussion("CS003", "teacher", "teacher",
                "first discussion", "111xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxx", "111xxxxxxxxxxxxxxxxx", dateFormatter.formatDateToString(new Date()));
        Discussion discussion2 = new Discussion("CS003", "teacher", "teacher",
                "second discussion", "111xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
                "xxxxxxxxxxxxxx", "222xxxxxxxxxxxxxxxxx", dateFormatter.formatDateToString(new Date()));
        discussionService.createDiscussion(discussion1);
        discussionService.createDiscussion(discussion2);
    }

    @Test
    public void createComment(){
        Comment comment1 = new Comment("CS003", 1, 0, "123", 2,
                "null", 0, "first comment on discussion 1", dateFormatter.formatDateToString(new Date()));
        Comment comment2 = new Comment("CS003", 1, 0, "123", 2,
                "null", 0, "second comment on discussion 1", dateFormatter.formatDateToString(new Date()));
        Comment comment3 = new Comment("CS003", 2, 0, "123", 2,
                "null", 0, "first comment on discussion 2", dateFormatter.formatDateToString(new Date()));
        commentService.createComment(comment1);
        commentService.createComment(comment2);
        commentService.createComment(comment3);
    }

    @Test
    public void generateID(){
        System.out.println(new FileIDBuilder().generateFileId());
    }

    @Test
    public void getUserByUserId(){
        System.out.println(userService.getUserByUserId("testUserId").getUsername());
    }

    @Test
    public void test(){
        List<Component> components = componentService.getAllComponentsOfCourse("CS003");
        for (Component component : components){
            List<String> fileId = componentService.getAllFilesOfComponent(component.getId());
            for (String id : fileId){
                File file = fileService.getFileByFileId(id);
                System.out.println(file.getPath());
            }
        }
    }
}
