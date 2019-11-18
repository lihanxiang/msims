package com.lee.msims.controller;

import com.lee.msims.pojo.common.User;
import com.lee.msims.service.common.FileService;
import com.lee.msims.service.common.UserService;
import com.lee.msims.util.FileIDBuilder;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("file")
public class FileController {

    private final FileIDBuilder fileIDBuilder;
    private final FileService fileService;
    private final UserService userService;

    public FileController(FileIDBuilder fileIDBuilder, FileService fileService, UserService userService) {
        this.fileIDBuilder = fileIDBuilder;
        this.fileService = fileService;
        this.userService = userService;
    }

    @RequestMapping(value = "pre-upload", method = RequestMethod.GET)
    public String preUpload(){
        return "fileUpload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file, Model model){
        User user = userService.getUserByUserId(
                (String)SecurityUtils.getSubject().getSession().getAttribute("userId"));
        String faculty = user.getFaculty();
        if (!file.isEmpty()){
            StringBuffer sb = new StringBuffer("C:\\Users\\94545\\Desktop\\file");
            sb.append("\\").append(faculty);
            String fileId = fileIDBuilder.generateFileId();
            String storePath = sb.toString();
            String fileName = file.getOriginalFilename();
            File filePath = new File(storePath, fileName);
            if (!filePath.getParentFile().exists()){
                filePath.getParentFile().mkdirs();
            }
            try {
                file.transferTo(new File(storePath + File.separator + fileName));
                com.lee.msims.pojo.common.File databaseFile =
                        new com.lee.msims.pojo.common.File(faculty, fileId, fileName, storePath);
                fileService.createFile(databaseFile);
            } catch (IOException e){
                e.printStackTrace();
                return "file/500";
            }
            return "file/uploadSuccess";
        } else {
            return "file/uploadFail";
        }
    }
}
