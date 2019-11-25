package com.lee.msims.controller;

import com.lee.msims.pojo.common.Course;
import com.lee.msims.pojo.common.User;
import com.lee.msims.service.common.CourseService;
import com.lee.msims.util.RoleConverter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("student")
public class StudentController {

    @Autowired
    private RoleConverter roleConverter;
    @Autowired
    private CourseService courseService;

    @RequiresRoles(value = "student")
    @RequestMapping(value = "home")
    public String home(Model model){
        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute("userId");
        model.addAttribute("userId", userId);
        return "student/home";
    }

    @RequestMapping(value = "courses", method = RequestMethod.GET)
    public String courses(Model model){
        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute("userId");
        List<String> courseCode = courseService.getCourseListByStudent(userId);
        List<Course> courses = new ArrayList<>();
        for (String code : courseCode){
            Course course = courseService.getCourseInfoByCourseCode(code);
            courses.add(course);
        }
        model.addAttribute("courses", courses);
        model.addAttribute("userId", userId);
        return "student/course";
    }
}
