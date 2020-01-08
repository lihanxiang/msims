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

    @RequiresRoles(value = {"student", "teacher"}, logical = Logical.OR)
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
        model.addAttribute("discussion", discussionService.getLatestFiveDiscussion(courseCode));

        return "teacher/course_detail";
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

    @RequiresRoles(value = {"student", "teacher"}, logical = Logical.OR)
    @RequestMapping(value = "{courseCode}/create-discussion")
    public String createDiscussion(Model model, @ModelAttribute("discussion") Discussion discussion,
                                    @PathVariable("courseCode") String courseCode){
        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute("userId");
        User user = userService.getUserByUserId(userId);
        discussion.setSponsor(user.getUsername());
        discussion.setSponsorId(user.getId());
        discussion.setCourseCode(courseCode);
        String snapshot = discussion.getContent();
        if (snapshot.length() > 50){
            discussion.setSnapshot(snapshot.substring(0, 49));
        } else {
            discussion.setSnapshot(snapshot + "...");
        }
        discussion.setDate(dateFormatter.formatDateToString(new Date()));
        discussionService.createDiscussion(discussion);
        model.addAttribute("userId", SecurityUtils.getSubject().getSession().getAttribute("userId"));
        model.addAttribute("courseCode", courseCode);
        model.addAttribute("msg", "Successfully create a discussion");
        return "redirect:/course/" + courseCode + "/discussion";
    }


    @RequiresRoles(value = {"student", "teacher"}, logical = Logical.OR)
    @RequestMapping(value = "{courseCode}/discussion", method = RequestMethod.GET)
    public String discussion(Model model, @PathVariable("courseCode") String courseCode) {
        List<Discussion> discussions = discussionService.getAllDiscussionOfCourse(courseCode);
        Map<Comment, List<Comment>> commentMap = new LinkedHashMap<>();
        Map<Discussion, List<Comment>> discussionMap = new LinkedHashMap<>();
        for(Discussion discussion : discussions) {
            List<Comment> comments = commentService.getAllCommentsOfDiscussion(discussion.getId());
            discussionMap.put(discussion, comments);
            for (Comment comment : comments){
                List<Comment> replies = commentService.getAllRepliesOfComment(discussion.getId(), comment.getId());
                commentMap.put(comment, replies);
            }
            /*if (!comments.isEmpty()){
                model.addAttribute("haveComment", 1);
            }*/
        }

        model.addAttribute("userId", SecurityUtils.getSubject().getSession().getAttribute("userId"));
        model.addAttribute("discussionMap", discussionMap);
        model.addAttribute("commentMap", commentMap);
        model.addAttribute("courseCode", courseCode);
        model.addAttribute("newComment", new Comment());
        model.addAttribute("newDiscussion", new Discussion());
        return "teacher/discussion";
    }

    @RequiresRoles(value = {"student", "teacher"}, logical = Logical.OR)
    @RequestMapping(value = "{courseCode}/create-comment")
    public String createComment(Model model, @ModelAttribute Comment comment,
                                @PathVariable("courseCode") String courseCode){
        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute("userId");
        User user = userService.getUserByUserId(userId);
        comment.setCommenter(user.getUsername());
        comment.setCommenterId(user.getId());
        comment.setDate(dateFormatter.formatDateToString(new Date()));
        commentService.createComment(comment);
        return "redirect:/course/" + courseCode + "/discussion";
    }

    @RequiresRoles(value = {"student", "teacher"}, logical = Logical.OR)
    @RequestMapping(value = "{courseCode}/reply-comment")
    public String replyComment(Model model, @ModelAttribute Comment comment,
                                @PathVariable("courseCode") String courseCode){
        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute("userId");
        User user = userService.getUserByUserId(userId);
        comment.setCommenter(user.getUsername());
        comment.setCommenterId(user.getId());
        comment.setDate(dateFormatter.formatDateToString(new Date()));
        commentService.replyComment(comment);

        return "redirect:/course/" + courseCode + "/discussion";
    }


}
