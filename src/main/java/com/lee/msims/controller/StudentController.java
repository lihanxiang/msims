package com.lee.msims.controller;

import com.lee.msims.pojo.coes.GPA;
import com.lee.msims.pojo.common.Course;
import com.lee.msims.pojo.common.File;
import com.lee.msims.pojo.common.User;
import com.lee.msims.pojo.moodle.Component;
import com.lee.msims.service.coes.GPAService;
import com.lee.msims.service.common.CourseService;
import com.lee.msims.service.common.UserService;
import com.lee.msims.service.moodle.ComponentService;
import com.lee.msims.util.GPACalculator;
import com.lee.msims.util.RoleConverter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("student")
@RequiresRoles(value = "student")
public class StudentController {

    @Autowired
    private RoleConverter roleConverter;
    @Autowired
    private CourseService courseService;
    @Autowired
    private GPAService gpaService;
    @Autowired
    private GPACalculator gpaCalculator;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String home(Model model){
        model.addAttribute("userId", (String)SecurityUtils.getSubject().getSession().getAttribute("userId"));
        return "student/home";
    }

    @RequestMapping(value = "moodle", method = RequestMethod.GET)
    public String moodle(Model model){
        model.addAttribute("userId", (String)SecurityUtils.getSubject().getSession().getAttribute("userId"));
        return "student/moodle";
    }

    @RequestMapping(value = "coes", method = RequestMethod.GET)
    public String coes(Model model){
        model.addAttribute("userId", (String)SecurityUtils.getSubject().getSession().getAttribute("userId"));
        return "student/coes";
    }

    @RequestMapping(value = "student-portal", method = RequestMethod.GET)
    public String studentPortal(Model model){
        model.addAttribute("userId", (String)SecurityUtils.getSubject().getSession().getAttribute("userId"));
        return "";
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

    @RequestMapping(value = "gpa", method = RequestMethod.GET)
    public String gpa(Model model){
        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute("userId");
        List<GPA> gradeList = gpaService.getGradeByUserId(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("gradeList", gradeList);
        model.addAttribute("GPA", gpaCalculator.calculateGPA(gradeList));
        return "student/gpa";
    }



    @RequestMapping(value = "my-info", method = RequestMethod.GET)
    public String myInfo(Model model){
        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute("userId");
        User user = userService.getUserByUserId(userId);
        model.addAttribute("user", user);
        return "student/my_info";
    }

    @RequestMapping(value = "pre-upload", method = RequestMethod.GET)
    public String preUpload(){
        return "student/file_submission";
    }
}
