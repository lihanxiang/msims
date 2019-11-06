package com.lee.msims.controller;

import com.lee.msims.pojo.common.User;
import com.lee.msims.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("user")
public class ForwardController {

    private final UserService userService;

    public ForwardController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@ModelAttribute User user, Model model){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserId(), user.getPassword());
        try {
            subject.login(token);
            User _user = userService.getUserByUserId(user.getUserId());
            subject.getSession().setAttribute("userId", _user.getUserId());
            model.addAttribute("userId", _user.getUserId());
        } catch (AuthenticationException e){
            model.addAttribute("message", "Invalid Username of Password");
            return "login";
        }
        return "home";
    }

    @RequestMapping(value = "upload")
    public String fileUpload(){
        return "fileUpload";
    }

    @RequestMapping(value = "get-login-page")
    public String preLogin(Model model){
        model.addAttribute("user", new User());
        return "login";
    }



    @RequestMapping(value = "home")
    public String home(Model model){
        System.out.println("yes");
        return "home";
    }

}
