package com.lee.msims.controller;

import com.lee.msims.pojo.common.File;
import com.lee.msims.pojo.common.User;
import com.lee.msims.pojo.moodle.*;
import com.lee.msims.service.common.CourseService;
import com.lee.msims.service.common.FileService;
import com.lee.msims.service.common.UserService;
import com.lee.msims.service.moodle.*;
import com.lee.msims.util.DateFormatter;
import com.lee.msims.util.FileIDBuilder;
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

    @RequestMapping(value = "home")
    public String home(Model model){
        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute("userId");
        model.addAttribute("userId", userId);
        model.addAttribute("courses", courseService.getCourseListByTeacher(userId));
        return "teacher/home";
    }

    @RequestMapping(value = "{courseCode}/course-detail", method = RequestMethod.GET)
    public String courseDetail(Model model, @PathVariable("courseCode") String courseCode){
        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute("userId");
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
        model.addAttribute("newComponent", new Component());
        model.addAttribute("newFile", new File());
        model.addAttribute("newAssignment", new Assignment());
        model.addAttribute("courses", courseService.getCourseListByTeacher(userId));

        //bulletin board
        model.addAttribute("messages", bulletinBoardService.getFiveLatestMessagesOnBoard(courseCode));

        //discussion
        model.addAttribute("discussion", discussionService.getLatestFiveDiscussion(courseCode));

        // assignment
        List<Assignment> assignments = assignmentService.getAssignmentsOfCourse(courseCode);
        Map<Assignment, File> assignmentMap = new LinkedHashMap<>();
        for (Assignment a : assignments){
            assignmentMap.put(a, fileService.getFileByFileId(a.getFileId()));
        }
        model.addAttribute("assignmentMap", assignmentMap);
        return "teacher/course_detail";
    }

    @RequestMapping(value = "{courseCode}/add-component")
    public String addFileComponent(@PathVariable("courseCode") String courseCode,
                                   @ModelAttribute Component component, Model model){
        component.setTime(new DateFormatter().formatDateToString(new Date()));
        componentService.addComponentToCourse(component);
        return "redirect:/teacher/" + courseCode + "/course-detail";
    }

    @RequestMapping(value = "{courseCode}/{componentId}/upload-file")
    public String upload(@PathVariable("courseCode") String courseCode,
                         @PathVariable("componentId") int componentId,
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
                componentService.addFileToComponent(componentId, fileId);
            } catch (IOException e){
                e.printStackTrace();
                return "file/500";
            }
            return "redirect:/teacher/" + courseCode + "/course-detail";
        } else {
            return "file/uploadFail";
        }
    }

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

        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute("userId");
        model.addAttribute("courses", courseService.getCourseListByTeacher(userId));
        model.addAttribute("userId", userId);
        model.addAttribute("id", userService.getUserByUserId(userId).getId());
        model.addAttribute("discussionMap", discussionMap);
        model.addAttribute("commentMap", commentMap);
        model.addAttribute("courseCode", courseCode);
        model.addAttribute("newComment", new Comment());
        model.addAttribute("newDiscussion", new Discussion());
        return "teacher/discussion";
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
        return "redirect:/teacher/" + courseCode + "/discussion";
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
        return "redirect:/teacher/" + courseCode + "/discussion";
    }

    @RequestMapping("{courseCode}/{assignmentId}/assignment")
    public String assessment(Model model, @PathVariable("assignmentId") int assignmentId,
                             @PathVariable("courseCode") String courseCode){

        List<String> studentIdSet = courseService.getStudentsOfCourse(courseCode);
        List<User> students = new ArrayList<>();
        for (String userId : studentIdSet){
            students.add(userService.getUserByUserId(userId));
        }
        model.addAttribute("students", students);

        List<Submission> submissions = submissionService.getSubmissionInAssignment(assignmentId);
        Map<User, Submission> submissionMap = new LinkedHashMap<>();
        for (Submission s : submissions){
            User student = userService.getUserByUserId(s.getStudentId());
            submissionMap.put(student, s);
            students.remove(student);
        }
        Map<Submission, Assessment> assessmentMap = new LinkedHashMap<>();
        List<Assessment> assessments = assessmentService.getAssessmentsInAssignment(assignmentId);
        for (Assessment a : assessments){
            assessmentMap.put(submissionService.getSubmissionById(a.getSubmissionId()), a);
        }

        Assignment assignment = assignmentService.getAssignmentById(assignmentId);

        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute("userId");
        model.addAttribute("assessmentMap", assessmentMap);
        model.addAttribute("newAssessment", new Assessment());
        model.addAttribute("file", fileService.getFileByFileId(assignment.getFileId()));
        model.addAttribute("assignment", assignment);
        model.addAttribute("submissionMap", submissionMap);
        model.addAttribute("courseCode", courseCode);
        model.addAttribute("assignmentId", assignmentId);
        model.addAttribute("userId", userId);
        model.addAttribute("courses", courseService.getCourseListByTeacher(userId));
        return "teacher/assignment";
    }

    @RequestMapping("{courseCode}/add-assignment")
    public String addAssignment(Model model, @PathVariable("courseCode") String courseCode,
                                @RequestParam("file") MultipartFile file){
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
                assignmentService.createAssignment(new Assignment(courseCode, fileId));
            } catch (IOException e){
                e.printStackTrace();
                return "file/500";
            }
            return "redirect:/teacher/" + courseCode + "/course-detail";
        } else {
            return "file/uploadFail";
        }
    }

    @RequestMapping("{courseCode}/{assignmentId}/write-assessment")
    public String writeAssessment(Model model, @PathVariable("assignmentId") int assignmentId,
                                  @ModelAttribute Assessment assessment, @PathVariable("courseCode") String courseCode){
        assessment.setDate(dateFormatter.formatDateToString(new Date()));
        assessmentService.createAssessment(assessment);
        submissionService.gradeSubmission(submissionService.getSubmissionById(assessment.getSubmissionId()));
        model.addAttribute("userId", SecurityUtils.getSubject().getSession().getAttribute("userId"));
        return "redirect:/teacher/" + courseCode + "/" + assignmentId + "/assignment";
    }

    @RequestMapping("{courseCode}/{assignmentId}/update-assessment")
    public String updateAssessment(Model model, @PathVariable("assignmentId") int assignmentId,
                                  @ModelAttribute Assessment assessment, @PathVariable("courseCode") String courseCode){
        assessment.setDate(dateFormatter.formatDateToString(new Date()));
        assessmentService.editAssessment(assessment);
        model.addAttribute("userId", SecurityUtils.getSubject().getSession().getAttribute("userId"));
        return "redirect:/teacher/" + courseCode + "/" + assignmentId + "/assessments";
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
        return "redirect:/teacher/" + courseCode + "/discussion";
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

        return "redirect:/teacher/" + courseCode + "/discussion";
    }

    @RequestMapping(value = "{courseCode}/bulletin-board")
    public String bulletinBoard(@PathVariable String courseCode, Model model){
        String userId = (String)SecurityUtils.getSubject().getSession().getAttribute("userId");
        model.addAttribute("courseCode", courseCode);
        model.addAttribute("userId", userId);
        model.addAttribute("messages", bulletinBoardService.getAllMessagesOnBoard(courseCode));
        model.addAttribute("newMessage", new BulletinBoardMessage());
        model.addAttribute("courses", courseService.getCourseListByTeacher(userId));
        return "teacher/bulletin_board";
    }

    @RequestMapping(value = "{courseCode}/post-message")
    public String postMessage(@PathVariable String courseCode, Model model,
                              @ModelAttribute BulletinBoardMessage bulletinBoardMessage){
        bulletinBoardMessage.setDate(dateFormatter.formatDateToString(new Date()));
        bulletinBoardService.postMessageOnBoard(bulletinBoardMessage);
        return "redirect:/teacher/" + courseCode + "/bulletin-board";
    }

    @RequestMapping(value = "{courseCode}/edit-message")
    public String editMessage(@PathVariable String courseCode, Model model,
                              @ModelAttribute BulletinBoardMessage bulletinBoardMessage){
        bulletinBoardMessage.setDate(dateFormatter.formatDateToString(new Date()));
        bulletinBoardService.editMessage(bulletinBoardMessage);
        return "redirect:/teacher/" + courseCode + "/bulletin-board";
    }
}
