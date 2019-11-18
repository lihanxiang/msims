package com.lee.msims.controller;

import com.lee.msims.service.common.FileService;
import com.lee.msims.util.FileIDBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("file")
public class FileController {

    @Autowired
    private FileIDBuilder fileIDBuilder;
    private FileService fileService;

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
                return "error/404";
            }
            return "success";
        } else {
            return "error";
        }
    }
}
