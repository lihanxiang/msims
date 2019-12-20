package com.lee.msims.controller;

import com.lee.msims.pojo.coes.GPA;
import com.lee.msims.pojo.common.Course;
import com.lee.msims.pojo.common.File;
import com.lee.msims.pojo.moodle.BulletinBoardMessage;
import com.lee.msims.pojo.moodle.Comment;
import com.lee.msims.pojo.moodle.Component;
import com.lee.msims.service.coes.GPAService;
import com.lee.msims.service.common.CourseService;
import com.lee.msims.service.common.FileService;
import com.lee.msims.service.common.UserService;
import com.lee.msims.service.moodle.BulletinBoardService;
import com.lee.msims.service.moodle.CommentService;
import com.lee.msims.service.moodle.ComponentService;
import com.lee.msims.util.GPACalculator;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("course")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private GPAService gpaService;
    @Autowired
    private GPACalculator gpaCalculator;
    @Autowired
    private ComponentService componentService;
    @Autowired
    private FileService fileService;
    @Autowired
    private BulletinBoardService bulletinBoardService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    // Common action
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

    // Student
    @RequestMapping(value = "gpa", method = RequestMethod.GET)
    public String gpa(Model model){
        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute("userId");
        List<GPA> gradeList = gpaService.getGradeByUserId(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("gradeList", gradeList);
        model.addAttribute("GPA", gpaCalculator.calculateGPA(gradeList));
        return "gpa";
    }

    @RequestMapping(value = "{courseCode}/course-detail", method = RequestMethod.GET)
    public String courseDetail(Model model, @PathVariable("courseCode") String courseCode){
        // course detail
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

        //bulletin board
        model.addAttribute("board-messages", bulletinBoardService.getAllMessagesOnBoard(courseCode));

        //discussion
        List<Comment> comments = commentService.getAllCommentsOfCourse(courseCode);
        Map<Comment, List<Comment>> commentMap = new LinkedHashMap<>();
        for (Comment comment : comments){
            List<Comment> replies = commentService.getAllRepliesOfComment(comment.getPid());
            commentMap.put(comment, replies);
        }
        if (!commentMap.values().isEmpty()){
            model.addAttribute("replies", 1);
        }
        model.addAttribute("comments", commentMap);

        return "teacher/course_detail";
    }

    @RequestMapping(value = "post-message-on-board", method = RequestMethod.POST)
    public String postMessageOnBoard(@ModelAttribute BulletinBoardMessage bulletinBoardMessage){
        bulletinBoardService.postMessageOnBoard(bulletinBoardMessage);
        return "";
    }
}
