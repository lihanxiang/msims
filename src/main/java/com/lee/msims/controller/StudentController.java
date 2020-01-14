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
import com.lee.msims.util.FileIDBuilder;
import com.lee.msims.util.GPACalculator;
import com.lee.msims.util.RoleConverter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("student")
@RequiresRoles(value = "student")
public class StudentController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    @Autowired
    private ComponentService componentService;
    @Autowired
    private FileService fileService;
    @Autowired
    private BulletinBoardService bulletinBoardService;
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private AssessmentService assessmentService;
    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private DateFormatter dateFormatter;
    @Autowired
    private DiscussionService discussionService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private FileIDBuilder fileIDBuilder;

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String home(Model model){
        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute("userId");
        List<Course> courses = new ArrayList<>();
        for (String c : courseService.getCourseListByStudent(userId)){
            courses.add(courseService.getCourseInfoByCourseCode(c));
        }
        model.addAttribute("courses", courses);
        model.addAttribute("userId", userId);
        return "student/home";
    }

    @RequestMapping(value = "{courseCode}/course-detail", method = RequestMethod.GET)
    public String courseDetail(Model model, @PathVariable("courseCode") String courseCode){
        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute("userId");
        List<Course> courses = new ArrayList<>();
        for (String c : courseService.getCourseListByStudent(userId)){
            courses.add(courseService.getCourseInfoByCourseCode(c));
        }
        model.addAttribute("courses", courses);

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
        model.addAttribute("userId", userId);
        model.addAttribute("course", courseService.getCourseInfoByCourseCode(courseCode));
        model.addAttribute("fileMap", fileMap);

        List<Submission> submissions = submissionService.getSubmissionByStudentId(userId);
        Map<String, File> files = new LinkedHashMap<>();
        Map<Submission, Assessment> assessmentMap = new LinkedHashMap<>();
        for (Submission s : submissions){
            Assessment assessment = assessmentService.getAssessmentBySubmissionId(s.getId());
            if (assessment != null){
                assessmentMap.put(s, assessment);
                files.put(s.getFileId(), fileService.getFileByFileId(s.getFileId()));
            }
        }
        model.addAttribute("assessmentMap", assessmentMap);
        model.addAttribute("files", files);

        //bulletin board
        model.addAttribute("messages", bulletinBoardService.getFiveLatestMessagesOnBoard(courseCode));

        //discussion
        model.addAttribute("discussion", discussionService.getLatestFiveDiscussion(courseCode));

        // assignment
        List<Assignment> assignments = assignmentService.getAssignmentsOfCourse(courseCode);
        Map<Integer, Submission> submittedAnswer = new LinkedHashMap<>();
        Map<Assignment, File> assignmentMap = new LinkedHashMap<>();
        for (Assignment a : assignments){
            assignmentMap.put(a, fileService.getFileByFileId(a.getFileId()));
            Submission submission = submissionService.getSubmissionByAssignmentIdAndStudentId(a.getId(), userId);
            if (submission != null){
                submittedAnswer.put(a.getId(), submission);
            }
        }
        model.addAttribute("submittedAnswer", submittedAnswer);
        model.addAttribute("assignmentMap", assignmentMap);
        return "student/course_detail";
    }

    @RequestMapping(value = "{courseCode}/{assignmentId}/upload-file")
    public String upload(@PathVariable("courseCode") String courseCode,
                         @PathVariable("assignmentId") int assignmentId,
                         @RequestParam("file") MultipartFile file, Model model){
        User user = userService.getUserByUserId(
                (String)SecurityUtils.getSubject().getSession().getAttribute("userId"));
        String faculty = user.getFaculty();
        if (!file.isEmpty()){
            StringBuffer sb = new StringBuffer("C:\\Users\\94545\\Desktop\\file");
            sb.append("\\").append(faculty);
            String fileId = fileIDBuilder.generateFileId();
            String fileName = file.getOriginalFilename();
            sb.append("\\").append(fileName);
            String storePath = sb.toString();
            java.io.File filePath = new java.io.File(storePath);
            if (!filePath.getParentFile().exists()){
                filePath.getParentFile().mkdirs();
            }
            try {
                file.transferTo(new java.io.File(storePath));
                com.lee.msims.pojo.common.File databaseFile =
                        new com.lee.msims.pojo.common.File(faculty, fileId, fileName, storePath,
                                dateFormatter.formatDateToString(new Date()));
                fileService.createFile(databaseFile);
                submissionService.createSubmission(new Submission(assignmentId, user.getUserId(), fileId, null,
                        dateFormatter.formatDateToString(new Date()), 0));
            } catch (IOException e){
                e.printStackTrace();
                return "file/500";
            }
            return "redirect:/student/" + courseCode + "/course-detail";
        } else {
            return "file/uploadFail";
        }
    }

    @RequestMapping(value = "{courseCode}/discussion", method = RequestMethod.GET)
    public String discussion(Model model, @PathVariable("courseCode") String courseCode) {
        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute("userId");
        List<Course> courses = new ArrayList<>();
        for (String c : courseService.getCourseListByStudent(userId)){
            courses.add(courseService.getCourseInfoByCourseCode(c));
        }
        model.addAttribute("courses", courses);

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

        model.addAttribute("userId", userId);
        model.addAttribute("id", userService.getUserByUserId(userId).getId());
        model.addAttribute("discussionMap", discussionMap);
        model.addAttribute("commentMap", commentMap);
        model.addAttribute("courseCode", courseCode);
        model.addAttribute("newComment", new Comment());
        model.addAttribute("newDiscussion", new Discussion());
        return "student/discussion";
    }

    @RequestMapping(value = "{courseCode}/create-discussion")
    public String createDiscussion(Model model, @ModelAttribute("discussion") Discussion discussion,
                                   @PathVariable("courseCode") String courseCode){
        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute("userId");
        User user = userService.getUserByUserId(userId);
        discussion.setSponsor(user.getUsername());
        discussion.setSponsorId(user.getId());
        discussion.setCourseCode(courseCode);
        String snapshot = discussion.getContent();
        if (snapshot.length() > 30){
            discussion.setSnapshot(snapshot.substring(0, 49));
        } else if (!snapshot.isEmpty()){
            discussion.setSnapshot(snapshot + "...");
        }
        discussion.setDate(dateFormatter.formatDateToString(new Date()));
        discussionService.createDiscussion(discussion);
        return "redirect:/student/" + courseCode + "/discussion";
    }

    @RequestMapping(value = "{courseCode}/edit-discussion")
    public String editDiscussion(Model model, @ModelAttribute("discussion") Discussion discussion,
                                 @PathVariable("courseCode") String courseCode) {
        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute("userId");
        User user = userService.getUserByUserId(userId);
        discussion.setSponsor(user.getUsername());
        discussion.setSponsorId(user.getId());
        discussion.setCourseCode(courseCode);
        String snapshot = discussion.getContent();
        if (snapshot.length() > 30){
            discussion.setSnapshot(snapshot.substring(0, 49));
        } else if (!snapshot.isEmpty()){
            discussion.setSnapshot(snapshot + "...");
        }
        discussionService.editDiscussion(discussion);
        return "redirect:/student/" + courseCode + "/discussion";
    }

    @RequestMapping(value = "{courseCode}/create-comment")
    public String createComment(Model model, @ModelAttribute Comment comment,
                                @PathVariable("courseCode") String courseCode){
        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute("userId");
        User user = userService.getUserByUserId(userId);
        comment.setCommenter(user.getUsername());
        comment.setCommenterId(user.getId());
        comment.setDate(dateFormatter.formatDateToString(new Date()));
        commentService.createComment(comment);
        return "redirect:/student/" + courseCode + "/discussion";
    }

    @RequestMapping(value = "{courseCode}/reply-comment")
    public String replyComment(Model model, @ModelAttribute Comment comment,
                               @PathVariable("courseCode") String courseCode){
        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute("userId");
        User user = userService.getUserByUserId(userId);
        comment.setCommenter(user.getUsername());
        comment.setCommenterId(user.getId());
        comment.setDate(dateFormatter.formatDateToString(new Date()));
        commentService.replyComment(comment);

        return "redirect:/student/" + courseCode + "/discussion";
    }
}
