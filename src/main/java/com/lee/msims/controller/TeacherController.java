package com.lee.msims.controller;

import com.lee.msims.pojo.common.Course;
import com.lee.msims.pojo.common.File;
import com.lee.msims.pojo.common.User;
import com.lee.msims.pojo.moodle.Component;
import com.lee.msims.service.common.CourseService;
import com.lee.msims.service.common.FileService;
import com.lee.msims.service.common.UserService;
import com.lee.msims.service.moodle.ComponentService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("teacher")
@RequiresRoles("teacher")
public class TeacherController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    @Autowired
    private ComponentService componentService;
    @Autowired
    private FileService fileService;

    @RequestMapping(value = "home")
    public String home(Model model){
        model.addAttribute("userId", SecurityUtils.getSubject().getSession().getAttribute("userId"));
        return "teacher/home";
    }

    @RequestMapping(value = "courses", method = RequestMethod.GET)
    public String courses(Model model){
        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute("userId");
        List<Course> courses = courseService.getCourseListByTeacher(userId);
        model.addAttribute("courses", courses);
        model.addAttribute("userId", userId);
        return "teacher/course";
    }

    @RequestMapping(value = "component", method = RequestMethod.GET)
    public String fileComponent(Model model){
        model.addAttribute("component", new Component());
        model.addAttribute("userId", SecurityUtils.getSubject().getSession().getAttribute("userId"));
        return "teacher/component";
    }

    @RequestMapping(value = "add-component", method = RequestMethod.POST)
    public String addFileComponent(@ModelAttribute Component component, Model model){
        //componentService.addComponentToCourse();


        return "";
    }

    @RequestMapping(value = "{courseCode}/course-detail", method = RequestMethod.GET)
    public String courseDetail(Model model, @PathVariable("courseCode") String courseCode){
        List<Component> components = componentService.getAllComponentsOfCourse(courseCode);
        Map<Component, File> fileMap = new HashMap<>();
        for (Component component : components){
            List<String> files = componentService.getAllFilesOfComponent(component.getId());
            for (String fileId : files){
                File file = fileService.getFileByFileId(fileId);
                fileMap.put(component, file);
            }
        }
        model.addAttribute("fileMap", fileMap);
        model.addAttribute("components", components);
        return "";
    }

    @RequestMapping(value = "my-info", method = RequestMethod.GET)
    public String myInfo(Model model){
        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute("userId");
        User user = userService.getUserByUserId(userId);
        model.addAttribute("user", user);
        return "teacher/my_info";
    }
}
