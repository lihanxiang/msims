package com.lee.msims.controller;

import com.lee.msims.pojo.common.User;
import com.lee.msims.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ForwardController {

    private final UserService userService;

    public ForwardController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/")
    public String fileUpload(){
        return "fileUpload";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(User user, Model model){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            subject.login(token);
            User _user = userService.getUserByUsername(user.getUsername());
            subject.getSession().setAttribute("id", _user.getId());
            subject.getSession().setAttribute("username", _user.getUsername());
            model.addAttribute("username", _user.getUsername());
        } catch (AuthenticationException e){
            model.addAttribute("info", "error");
            return "login";
        }
        return "index";
    }
}
