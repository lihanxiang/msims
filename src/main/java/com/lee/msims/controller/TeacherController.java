package com.lee.msims.controller;

import com.lee.msims.pojo.common.Course;
import com.lee.msims.pojo.common.User;
import com.lee.msims.pojo.moodle.*;
import com.lee.msims.service.common.CourseService;
import com.lee.msims.service.common.FileService;
import com.lee.msims.service.common.UserService;
import com.lee.msims.service.moodle.*;
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

    /*@RequestMapping(value = "{courseCode}/course-detail", method = RequestMethod.GET)
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
    }*/

    /*@RequestMapping(value = "#{courseCode}/bulletin-board", method = RequestMethod.GET)
    public String bulletinBoard(@PathVariable String courseCode, Model model){
        model.addAttribute("bulletin-board", bulletinBoardService.getBoard(courseCode));
        return "";
    }

    @RequestMapping(value = "post-message-on-board", method = RequestMethod.POST)
    public String postMessageOnBoard(@ModelAttribute BulletinBoardMessage bulletinBoardMessage){
        bulletinBoardService.postMessageOnBoard(bulletinBoardMessage);
        return "";
    }*/

    @RequestMapping("{courseCode}/{assignmentId}/assignment")
    public String assessment(Model model, @PathVariable("assignmentId") int assignmentId,
                             @PathVariable("courseCode") String courseCode){

        List<String> studentIdSet = courseService.getStudentsOfCourse(courseCode);
        List<User> students = new ArrayList<>();
        for (String userId : studentIdSet){
            students.add(userService.getUserByUserId(userId));
        }

        List<Submission> submissions = submissionService.getSubmissionInAssignment(assignmentId);
        Map<User, Submission> submissionMap = new LinkedHashMap<>();
        for (Submission s : submissions){
            User student = userService.getUserById(s.getStudentId());
            submissionMap.put(student, s);
            students.remove(student);
        }
        Map<Submission, Assessment> assessmentMap = new LinkedHashMap<>();
        List<Assessment> assessments = assessmentService.getAssessmentsInAssignment(assignmentId);
        for (Assessment a : assessments){
            assessmentMap.put(submissionService.getSubmissionById(a.getSubmissionId()), a);
        }

        model.addAttribute("students", students);
        model.addAttribute("assessmentMap", assessmentMap);
        model.addAttribute("newAssessment", new Assessment());
        model.addAttribute("assignment", assignmentService.getAssignmentById(assignmentId));
        model.addAttribute("submissionMap", submissionMap);
        model.addAttribute("courseCode", courseCode);
        model.addAttribute("assignmentId", assignmentId);
        model.addAttribute("userId", SecurityUtils.getSubject().getSession().getAttribute("userId"));
        return "teacher/assignment";
    }

    @RequestMapping("{courseCode}/{assignmentId}/write-assessment")
    public String writeAssessment(Model model, @PathVariable("assignmentId") int assignmentId,
                                  @ModelAttribute Assessment assessment, @PathVariable("courseCode") String courseCode){
        assessment.setDate(dateFormatter.formatDateToString(new Date()));
        assessmentService.createAssessment(assessment);
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

    @RequestMapping("{courseCode}/{assignmentId}/assessments")
    public String assessments(Model model, @PathVariable("assignmentId") int assignmentId,
                             @PathVariable("courseCode") String courseCode){
        Map<Submission, Assessment> assessmentMap = new LinkedHashMap<>();
        List<Assessment> assessments = assessmentService.getAssessmentsInAssignment(assignmentId);
        for (Assessment a : assessments){
            assessmentMap.put(submissionService.getSubmissionById(a.getSubmissionId()), a);
        }
        model.addAttribute("title", assignmentService.getAssignmentById(assignmentId).getTitle());
        model.addAttribute("assessmentMap", assessmentMap);
        model.addAttribute("courseCode", courseCode);
        model.addAttribute("assignmentId", assignmentId);
        model.addAttribute("newAssessment", new Assessment());
        model.addAttribute("userId", SecurityUtils.getSubject().getSession().getAttribute("userId"));
        return "teacher/assessment";
    }


    @RequestMapping(value = "post-message-on-board", method = RequestMethod.POST)
    public String postMessageOnBoard(@ModelAttribute BulletinBoardMessage bulletinBoardMessage){
        bulletinBoardService.postMessageOnBoard(bulletinBoardMessage);
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
