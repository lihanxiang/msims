package com.lee.msims.controller;

import com.lee.msims.pojo.common.User;
import com.lee.msims.service.common.UserService;
import com.lee.msims.util.RoleConverter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private  RoleConverter roleConverter;
    @Autowired
    private  UserService userService;

    @RequestMapping(value = "login-page", method = RequestMethod.GET)
    public String loginPage(Model model){
        model.addAttribute("user", new User());
        return "login";
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
            return "login";
        }
        if (roleSet.contains("student")){
            return "student/home";
        } else if (roleSet.contains("teacher")){
            return "teacher/home";
        } else {
            return "admin/home";
        }
    }

    @RequestMapping(value = "logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "index";
    }

}
