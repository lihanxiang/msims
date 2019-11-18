package com.lee.msims.controller;

import com.lee.msims.pojo.common.User;
import com.lee.msims.service.common.UserService;
import com.lee.msims.util.RoleConverter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.Set;

@Controller
@RequestMapping("user")
public class UserController {

    private final RoleConverter roleConverter;
    private final UserService userService;

    public UserController(UserService userService, RoleConverter roleConverter) {
        this.userService = userService;
        this.roleConverter = roleConverter;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@ModelAttribute User user, Model model){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserId(), user.getPassword());
        Set<String> roleSet;
        try {
            subject.login(token);
            User _user = userService.getUserByUserId(user.getUserId());
            roleSet = roleConverter.convertRolesIntoRoleSet(_user.getRoles());
            subject.getSession().setAttribute("userId", _user.getUserId());
            model.addAttribute("userId", _user.getUserId());
        } catch (AuthenticationException e){
            model.addAttribute("message", "Invalid Username of Password");
            return "common/login";
        }
        if (roleSet.contains("student")){
            return "student/home";
        } else if (roleSet.contains("teacher")){
            return "teacher/home";
        } else {
            return "admin/home";
        }
    }

    @RequestMapping(value = "upload")
    public String fileUpload(){
        return "fileSubmission";
    }

    @RequestMapping(value = "login-page")
    public String preLogin(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping(value = "home")
    public String home(){
        return "home";
    }

}
