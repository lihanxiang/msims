package com.lee.msims.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
public class UploadController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        if (!file.isEmpty()){
            String contextPath = request.getContextPath();
            String servletPath = request.getServletPath();
            String scheme = request.getScheme();

            String storePath = "C:\\Users\\94545\\Desktop\\file";
            String fileName = file.getOriginalFilename();
            File filePath = new File(storePath, fileName);
            if (!filePath.getParentFile().exists()){
                filePath.getParentFile().mkdirs();
            }
            try {
                file.transferTo(new File(storePath + File.separator + fileName));
            } catch (IOException e){
                e.printStackTrace();
                return "error";
            }
            return "success";
        } else {
            return "error";
        }
    }

}
