package com.lee.msims.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("teacher")
@RequiresRoles("teacher")
public class TeacherController {


    @RequestMapping(value = "home")
    public String home(Model model){
        String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
        model.addAttribute("userId", userId);
        return "teacher/home";
    }
}
