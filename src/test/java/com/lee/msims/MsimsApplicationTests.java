package com.lee.msims;

import com.lee.msims.pojo.common.File;
import com.lee.msims.pojo.common.User;
import com.lee.msims.pojo.moodle.Component;
import com.lee.msims.service.common.FileService;
import com.lee.msims.service.common.UserService;
import com.lee.msims.service.moodle.ComponentService;
import com.lee.msims.util.FileIDBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
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
