package com.lee.msims.controller;

import com.lee.msims.pojo.common.Course;
import com.lee.msims.pojo.common.File;
import com.lee.msims.pojo.common.User;
import com.lee.msims.pojo.moodle.Component;
import com.lee.msims.service.common.CourseService;
import com.lee.msims.service.common.FileService;
import com.lee.msims.service.common.UserService;
import com.lee.msims.service.moodle.ComponentService;
import com.lee.msims.util.DateFormatter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.*;

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

    @RequestMapping(value = "{courseCode}/component", method = RequestMethod.GET)
    public String fileComponent(@PathVariable("courseCode") String courseCode, Model model){
        model.addAttribute("component", new Component());
        model.addAttribute("courseCode", courseCode);
        model.addAttribute("userId", SecurityUtils.getSubject().getSession().getAttribute("userId"));
        return "teacher/component";
    }

    @RequestMapping(value = "{courseCode}/add-component")
    public String addFileComponent(@PathVariable("courseCode") String courseCode,
                                   @ModelAttribute Component component, Model model){
        component.setTime(new DateFormatter().formatDateToString(new Date()));
        componentService.addComponentToCourse(component);
        return "redirect:/teacher/" + courseCode + "/course-detail";
    }

    @RequestMapping(value = "{courseCode}/course-detail", method = RequestMethod.GET)
    public String courseDetail(Model model, @PathVariable("courseCode") String courseCode){
        List<Component> components = componentService.getAllComponentsOfCourse(courseCode);
        Map<Component, List<File>> fileMap = new LinkedHashMap<>();
        for (Component component : components){
            List<String> fileName = componentService.getAllFilesOfComponent(component.getId());
            List<File> files = new ArrayList<>();
            for (String fileId : fileName){
                File file = fileService.getFileByFileId(fileId);
                files.add(file);
            }
            fileMap.put(component, files);
        }
        model.addAttribute("userId", SecurityUtils.getSubject().getSession().getAttribute("userId"));
        model.addAttribute("course", courseService.getCourseInfoByCourseCode(courseCode));
        model.addAttribute("fileMap", fileMap);
        return "teacher/course_detail";
    }

    @RequestMapping(value = "my-info", method = RequestMethod.GET)
    public String myInfo(Model model){
        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute("userId");
        User user = userService.getUserByUserId(userId);
        model.addAttribute("user", user);
        return "teacher/my_info";
    }
}
