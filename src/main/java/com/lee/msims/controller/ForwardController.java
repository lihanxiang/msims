package com.lee.msims.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ForwardController {

    @RequestMapping(value = "/")
    public String fileUpload(){
        return "fileUpload";
    }
}
