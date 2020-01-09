package com.lee.msims.controller;

import com.lee.msims.pojo.coes.GPA;
import com.lee.msims.pojo.common.Course;
import com.lee.msims.pojo.common.File;
import com.lee.msims.pojo.common.User;
import com.lee.msims.pojo.moodle.*;
import com.lee.msims.service.coes.GPAService;
import com.lee.msims.service.common.CourseService;
import com.lee.msims.service.common.FileService;
import com.lee.msims.service.common.UserService;
import com.lee.msims.service.moodle.*;
import com.lee.msims.util.DateFormatter;
import com.lee.msims.util.GPACalculator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
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
    @Autowired
    private DiscussionService discussionService;
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private AssessmentService assessmentService;
    @Autowired
    private DateFormatter dateFormatter;

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

    @RequiresRoles(value = "student")
    @RequestMapping(value = "gpa", method = RequestMethod.GET)
    public String gpa(Model model){
        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute("userId");
        List<GPA> gradeList = gpaService.getGradeByUserId(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("gradeList", gradeList);
        model.addAttribute("GPA", gpaCalculator.calculateGPA(gradeList));
        return "gpa";
    }

    /*
        List<Comment> comments = commentService.getAllCommentsOfDiscussion();
        Map<Comment, List<Comment>> commentMap = new LinkedHashMap<>();
        for (Comment comment : comments){
            List<Comment> replies = commentService.getAllRepliesOfComment(comment.getPid());
            commentMap.put(comment, replies);
        }
        if (!commentMap.values().isEmpty()){
            model.addAttribute("replies", 1);
        }
        model.addAttribute("comments", commentMap);
     */

    @RequestMapping(value = "{courseCode}/discussion-detail")
    public String discussionDetail(Model model, @PathVariable("courseCode") String courseCode){
        model.addAttribute("userId", SecurityUtils.getSubject().getSession().getAttribute("userId"));
        model.addAttribute("courseCode", courseCode);
        model.addAttribute("discussion", new Discussion());
        return "teacher/create_discussion";
    }



}
