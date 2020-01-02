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

import java.util.*;

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
        Comment comment1 = new Comment("CS003", 1, 2, "123", 2,
                "123", 2, "first reply 111", dateFormatter.formatDateToString(new Date()));
        Comment comment2 = new Comment("CS003", 1, 2, "123", 2,
                "123", 2, "second reply 222", dateFormatter.formatDateToString(new Date()));
        Comment comment3 = new Comment("CS003", 2, 3, "123", 2,
                "123", 2, "third reply 333", dateFormatter.formatDateToString(new Date()));
        commentService.replyComment(comment1);
        commentService.replyComment(comment2);
        commentService.replyComment(comment3);
    }

    @Test
    public void generateID(){
        System.out.println(new FileIDBuilder().generateFileId());
    }

    @Test
    public void select(){
        List<Discussion> discussions = discussionService.getAllDiscussionOfCourse("CS003");
        Map<Comment, List<Comment>> commentMap = new LinkedHashMap<>();
        Map<Discussion, Map<Comment, List<Comment>>> discussionMap = new LinkedHashMap<>();
        List<Comment> comments;
        for(Discussion discussion : discussions) {
            //System.out.println(discussion.getTitle());
            comments = commentService.getAllCommentsOfDiscussion(discussion.getId());
            for (Comment comment : comments){
                //System.out.println("    comment: " + comment.getContent());
                List<Comment> replies = commentService.getAllRepliesOfComment(discussion.getId(), comment.getId());
                commentMap.put(comment, replies);
                for (Comment reply : replies){
                    //System.out.println("        reply: " + reply.getContent());
                }
            }
            discussionMap.put(discussion, commentMap);
            //System.out.println();
        }

        Set<Discussion> set = discussionMap.keySet();
        for (Iterator<Discussion> iterator = set.iterator(); iterator.hasNext();){
            Discussion d = iterator.next();
            System.out.println(d.getTitle());
            Map<Comment, List<Comment>> commentMap1 = discussionMap.get(d);
            Set<Map.Entry<Comment, List<Comment>>> entrySet = commentMap.entrySet();
            for (Iterator<Map.Entry<Comment, List<Comment>>> iterator1 = entrySet.iterator(); iterator1.hasNext();){
                Map.Entry<Comment, List<Comment>> entry = iterator1.next();
                System.out.println("    comment: " + entry.getKey().getContent());
                for (Comment c : entry.getValue()){
                    System.out.println("        reply:" + c.getContent());
                }
            }
            System.out.println();
        }
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
